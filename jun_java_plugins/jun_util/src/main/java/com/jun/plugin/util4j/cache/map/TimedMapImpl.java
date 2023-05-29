package com.jun.plugin.util4j.cache.map;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.thread.NamedThreadFactory;

/**
 * 缓存键值对
 * 默认30秒自动清理,其它则访问时触发清理
 * 类似需求实现
 * http://ifeve.com/google-guava-cachesexplained/
 * @author Administrator
 * @param <K> 
 * @param <V>
 */
public class TimedMapImpl<K,V> implements TimedMap<K, V>{
	protected Logger log=LoggerFactory.getLogger(getClass());
	private final Executor lisenterExecutor;
	private final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();
	private final Map<K,TimedEntry<K,V>> entryMap=new HashMap<>();
	private volatile boolean iteratorUpdate;//对map集合进行迭代时,是否刷新时间
	
	/**
	 * 默认最大2个线程处理监听器
	 * 迭代的时候也更新ttl
	 */
	public TimedMapImpl(){
		this(Executors.newFixedThreadPool(2,new NamedThreadFactory("TimedMapLisenterExecutor", true)),true);
	}
	
	/**
	 * @param iteratorUpdate 是否在迭代的时候也更新ttl
	 */
	public TimedMapImpl(boolean iteratorUpdate){
		this(Executors.newFixedThreadPool(2,new NamedThreadFactory("TimedMapLisenterExecutor", true)), iteratorUpdate);
	}
	
	/**
	 * 建议线程池固定大小,否则移除事件过多会消耗很多线程资源
	 * @param lisenterExecutor 指定处理超时监听的executor
	 * @param iteratorUpdate 是否在迭代的时候也更新ttl
	 */
	public TimedMapImpl(Executor lisenterExecutor,boolean iteratorUpdate){
		Objects.requireNonNull(lisenterExecutor);
		this.lisenterExecutor=lisenterExecutor;
		this.iteratorUpdate=iteratorUpdate;
	}
	
	@SuppressWarnings("hiding")
	class TimedEntry<K,V> implements Entry<K, V>{
	
		/**
		 * 创建时间
		 */
		private final long createTime=System.currentTimeMillis();
		
		/**
		 * 上次活动
		 */
		private long lastActiveTime;
		
		/**
		 * 最大不活动间隔时间,毫秒
		 * <=0则表示永不过期
		 */
		private long ttl;
		
		/**
		 *缓存对象
		 */
		private final K key;
		
		private V value;
		/**
		 * 监听器
		 */
		private EventListener<K,V> listener; 
	
		TimedEntry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
			this.lastActiveTime=createTime;
		}
		
		TimedEntry(K key, V value,long ttl) {
			super();
			this.key = key;
			this.value = value;
			this.ttl=ttl;
			this.lastActiveTime=createTime;
		}
	
		public long getLastActiveTime() {
			return lastActiveTime;
		}
	
		public void setLastActiveTime(long lastActiveTime) {
			this.lastActiveTime = lastActiveTime;
		}
		
		public long getTtl() {
			return ttl;
		}

		public void setTtl(long ttl) {
			this.ttl = ttl;
		}

		public long getCreateTime() {
			return createTime;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			this.value = value;
			return value;
		}

		public boolean isTimeOut()
		{
			long now=System.currentTimeMillis();
			if(getTtl()>0)
			{
				return now>=getLastActiveTime()+getTtl();
			}else
			{//永不过期
				return false;
			}
		}

		public EventListener<K, V> getListener() {
			return listener;
		}

		public void setListener(EventListener<K, V> listener) {
			this.listener = listener;
		}
		
		private boolean eqOrBothNull(Object a, Object b)
	    {
		if (a == b)
		    return true;
		else if (a == null)
		    return false;
		else
		    return a.equals(b);
	    }
		
		@SuppressWarnings("unchecked")
		public boolean equals(Object o)
		    {
			if (o instanceof Map.Entry)
			    {
				TimedEntry<K,V> other = (TimedEntry<K,V>)o;
				return
				    eqOrBothNull( this.getKey(), other.getKey() ) &&
				    eqOrBothNull( this.getValue(), other.getValue() );
			    }
			else 
			    return false;
		    }

		public int hashCode()
		{
			  return 
			    (this.getKey()   == null ? 0 : this.getKey().hashCode()) ^
			    (this.getValue() == null ? 0 : this.getValue().hashCode());
		 }

		@Override
		public String toString() {
			return "TimedEntry [createTime=" + createTime + ", lastActiveTime=" + lastActiveTime + ", ttl=" + ttl
					+ ", key=" + key + ", value=" + value + ", listener=" + listener + "]";
		}
	}

	transient volatile Runnable cleanTask;
	
	/**
	 * 获取清理超时的任务,执行后将会触发监听器执行
	 * @return
	 */
	public Runnable getCleanTask()
	{
		if(cleanTask==null)
		{
			cleanTask=new CleanTask();
		}
		return cleanTask;
	}
	
	private class CleanTask implements Runnable{
		@Override
		public void run() {
			try {
				cleanExpire();
			} catch (Throwable e) {
				log.error(e.getMessage(),e);
			}
		}
	}
	
	@Override
	public Map<K, V> cleanExpire() {
		rwLock.readLock().lock();
		Map<K,V> map=new HashMap<>();
		Set<K> removeKeys=new HashSet<K>();
		try {
			for(java.util.Map.Entry<K, TimedMapImpl<K, V>.TimedEntry<K, V>> entry:entryMap.entrySet())
			{
				if(entry.getValue().isTimeOut())
				{
					removeKeys.add(entry.getKey());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			rwLock.readLock().unlock();
		}
		if(!removeKeys.isEmpty())
		{
			rwLock.writeLock().lock();
			try {
				for(Object key:removeKeys)
				{
					TimedEntry<K, V> entry=removeAndListener(key,true);
					if(entry!=null)
					{
						map.put(entry.key, entry.value);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}finally {
				removeKeys.clear();
				rwLock.writeLock().unlock();
			}
		}
		return map;
	}

	/**
	 * 移除缓存对象并通知事件
	 * @param key
	 * @param expire 是否超时才执行的移除
	 * @return
	 */
	protected TimedEntry<K, V> removeAndListener(Object key,final boolean expire)
	{
		final TimedEntry<K, V> entry=entryMap.remove(key);
		try {
			if(entry!=null)
			{//通知被移除
				final EventListener<K, V> listener=entry.listener;
				entry.setListener(null);
				if(listener!=null)
				{
					lisenterExecutor.execute(new Runnable() {
						@Override
						public void run() {
							try {
								listener.removed(entry.getKey(),entry.getValue(),expire);
							} catch (Throwable e) {
								log.error(e.getMessage(),e);
							}
						}
					});
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return entry;
	}

	public boolean isIteratorUpdate() {
		return iteratorUpdate;
	}

	public void setIteratorUpdate(boolean iteratorUpdate) {
		this.iteratorUpdate = iteratorUpdate;
	}

	@Override
	public int size() {
		rwLock.readLock().lock();
		try {
			return entryMap.size();
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	@Override
	public boolean isEmpty() {
		rwLock.readLock().lock();
		try {
			return entryMap.isEmpty();
		} finally {
			rwLock.readLock().unlock();
		}
	}

	@Override
	public boolean containsKey(Object key) {
		return get(key)==null;
	}

	@Override
	public boolean containsValue(Object value) {
		try {
			Iterator<Entry<K,V>> i = entrySet().iterator();//有锁
	        if (value==null) {
	            while (i.hasNext()) {
	                Entry<K,V> e = i.next();
	                if (e.getValue()==null)
	                    return true;
	            }
	        } else {
	            while (i.hasNext()) {
	                Entry<K,V> e = i.next();
	                if (value.equals(e.getValue()))
	                    return true;
	            }
	        }
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
        return false;
	}

	@Override
	public V put(K key, V value) {
        return put(key, value,0);
	}

	@Override
	public V put(K key, V value, long ttl) {
        return put(key, value, ttl, null);
	}
	
	@Override
	public V put(K key, V value, long ttl,EventListener<K, V> listener) {
		if (key == null || value == null) throw new NullPointerException();
		rwLock.writeLock().lock();
		try {
			TimedEntry<K,V> entry=new TimedEntry<K,V>(key, value,ttl);
			entry.setListener(listener);
			entryMap.put(key,entry);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			rwLock.writeLock().unlock();
		}
        return value;
	}

	@Override
	public V get(Object key) {
		rwLock.readLock().lock();
		V result=null;
		boolean remove=false;
		try {
			TimedEntry<K, V> e=entryMap.get(key);
			if(e!=null)
			{
				e.setLastActiveTime(System.currentTimeMillis());
				if(e.isTimeOut())
				{
					remove=true;
				}else
				{
					result=e.getValue();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			rwLock.readLock().unlock();
		}
		if(remove)
		{
			rwLock.writeLock().lock();
			try {
				removeAndListener(key,true);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}finally {
				rwLock.writeLock().unlock();
			}
		}
		return result;
	}

	@Override
	public V getBy(K key) {
		return get(key);
	}

	@Override
	public V remove(Object key) {
		rwLock.writeLock().lock();
		TimedEntry<K,V> value=null;
		try {
			value=removeAndListener(key,false);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			rwLock.writeLock().unlock();
		}
		return value==null?null:value.getValue();
	}

	@Override
	public V removeBy(K key) {
		return remove(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if (m == null) throw new NullPointerException();
		rwLock.writeLock().lock();
		try {
			for(java.util.Map.Entry<? extends K, ? extends V> e:m.entrySet())
			{
				TimedEntry<K,V> addEntry;
				if(e instanceof TimedEntry)
				{
					addEntry=(TimedEntry<K, V>) e;
				}else
				{
					addEntry=new TimedEntry<K,V>(e.getKey(), e.getValue());
				}
				entryMap.put(e.getKey(), addEntry);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			rwLock.writeLock().unlock();
		}
	}

	@Override
	public void clear() {
		rwLock.writeLock().lock();
		try {
			entryMap.clear();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			rwLock.writeLock().unlock();
		}
	}

	class EntryIterator implements Iterator<Entry<K,V>>{
		Iterator<java.util.Map.Entry<K, TimedMapImpl<K, V>.TimedEntry<K, V>>> it=entryMap.entrySet().iterator();
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		@Override
		public Entry<K,V> next() {
			Entry<K, TimedMapImpl<K, V>.TimedEntry<K, V>> value=it.next();
			if(value!=null)
			{
				TimedEntry<K, V> entry=value.getValue();
				if(entry!=null)
				{
					if(iteratorUpdate)
					{
						entry.setLastActiveTime(System.currentTimeMillis());
					}
				}
				return entry;
			}
			return null;
		}
		@Override
		public void remove() {
			it.remove();
		}
	}

	
	class KeyIterator implements Iterator<K>{
		EntryIterator it=new EntryIterator();
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		@Override
		public K next() {
			Entry<K,V> entry=it.next();
			if(entry!=null)
			{
				return entry.getKey();
			}
			return null;
		}
		@Override
		public void remove() {
			it.remove();
		}
	}
	
	class ValueIterator implements Iterator<V>{
		EntryIterator it=new EntryIterator();
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		@Override
		public V next() {
			Entry<K,V> entry=it.next();
			if(entry!=null)
			{
				return entry.getValue();
			}
			return null;
		}
		@Override
		public void remove() {
			it.remove();
		}
	}

    transient volatile Set<K>        keySet;
    transient volatile Collection<V> values;
    transient Set<Map.Entry<K,V>> entrySet;
    
	class TimedKeySet extends AbstractSet<K>
	{
		@Override
		public Iterator<K> iterator() {
			return new KeyIterator();
		}

		@Override
		public int size() {
			return TimedMapImpl.this.size();
		}
		
		public boolean isEmpty() {
            return TimedMapImpl.this.isEmpty();
        }

        public void clear() {
        	TimedMapImpl.this.clear();
        }

        public boolean contains(Object k) {
            return TimedMapImpl.this.containsKey(k);
        }
	}
	
	class TimedValues extends AbstractCollection<V>
	{
		@Override
		public Iterator<V> iterator() {
			return new ValueIterator();
		}

		@Override
		public int size() {
			return TimedMapImpl.this.size();
		}
		
		public boolean isEmpty() {
            return TimedMapImpl.this.isEmpty();
        }

        public void clear() {
        	TimedMapImpl.this.clear();
        }

        public boolean contains(Object k) {
            return TimedMapImpl.this.containsKey(k);
        }
	}
	
	class TimedEntrySet extends AbstractSet<Entry<K,V>>
	{
		@Override
		public Iterator<Entry<K,V>> iterator() {
			return new EntryIterator();
		}
	
		@Override
		public int size() {
			return TimedMapImpl.this.size();
		}
		public boolean isEmpty() {
            return TimedMapImpl.this.isEmpty();
        }

        public void clear() {
        	TimedMapImpl.this.clear();
        }
		
		public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>) o;
            Object key = e.getKey();
            return TimedMapImpl.this.containsKey(key);
        }
        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                Object key = e.getKey();
                return  TimedMapImpl.this.remove(key)!= null;
            }
            return false;
        }
	}


	@Override
	public Set<K> keySet() {
		if(keySet==null)
		{
			keySet=new TimedKeySet();
		}
		return keySet;
	}

	@Override
	public Collection<V> values() {
		if(values==null)
		{
			values=new TimedValues();
		}
		return values;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		if(entrySet==null)
		{
			entrySet=new TimedEntrySet();
		}
		return entrySet;
	}

	@Override
	public V updateTTL(K key, long ttl) {
		rwLock.writeLock().lock();
		V result=null;
		try {
			TimedEntry<K, V> e=entryMap.get(key);
			if(e!=null)
			{
				e.setLastActiveTime(System.currentTimeMillis());
				if(e.isTimeOut())
				{//已过期
					removeAndListener(key,true);
				}else
				{
					e.setLastActiveTime(System.currentTimeMillis());
					e.setTtl(ttl);
					result=e.getValue();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			rwLock.writeLock().unlock();
		}
		return result;
	}

	/**
	 * 获取过期时间,此访问不会更新活动时间
	 */
	@Override
	public long getExpireTime(K key) {
		rwLock.readLock().lock();
		boolean remove=false;
		long result=-1;//过期移除
		try {
			TimedEntry<K, V> e=entryMap.get(key);
			if(e!=null)
			{//未过期
				if(e.getTtl()>0)
				{//有过期时间
					if(e.isTimeOut())
					{//过期移除
						remove=true;
					}
					result= e.getLastActiveTime()+e.getTtl()-System.currentTimeMillis();
				}else
				{//永不过期
					result= 0;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			rwLock.readLock().unlock();
		}
		if(remove)
		{
			rwLock.writeLock().lock();
			try {
				removeAndListener(key,true);
				result= -1;
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}finally {
				rwLock.writeLock().unlock();
			}
		}
		return result;
	}
	
	@Override
	public V setEventListener(K key,EventListener<K, V> lisnener) {
		rwLock.readLock().lock();
		V result=null;
		boolean remove=false;
		try {
			TimedEntry<K, V> e=entryMap.get(key);
			if(e!=null)
			{
				e.setLastActiveTime(System.currentTimeMillis());
				if(e.isTimeOut())
				{
					remove=true;
				}else
				{
					e.setListener(lisnener);
					result= e.getValue();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally {
			rwLock.readLock().unlock();
		}
		if(remove)
		{
			rwLock.writeLock().lock();
			try {
				removeAndListener(key,true);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}finally {
				rwLock.writeLock().unlock();
			}
		}
		return result;
	}
}
