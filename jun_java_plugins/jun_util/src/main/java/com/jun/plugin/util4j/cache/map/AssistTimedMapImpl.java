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
public class AssistTimedMapImpl<K,V> implements AssistTimedMap<K, V>{
	protected Logger log=LoggerFactory.getLogger(getClass());
	private final Executor lisenterExecutor;
	private final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();
	private final Map<K,AssistTimedEntry<K,V>> entryMap=new HashMap<>();
	private volatile boolean iteratorUpdate;//对map集合进行迭代时,是否刷新时间
	
	/**
	 * 默认最大2个线程处理监听器
	 * 迭代的时候也更新ttl
	 */
	public AssistTimedMapImpl(){
		this(Executors.newFixedThreadPool(2,new NamedThreadFactory("TimedMapCleanExecutor", true)),true);
	}
	
	/**
	 * @param iteratorUpdate 是否在迭代的时候也更新ttl
	 */
	public AssistTimedMapImpl(boolean iteratorUpdate){
		this(Executors.newFixedThreadPool(2,new NamedThreadFactory("TimedMapCleanExecutor", true)), iteratorUpdate);
	}
	
	/**
	 * 建议线程池固定大小,否则移除事件过多会消耗很多线程资源
	 * @param cleanExecutor 指定处理超时监听的executor
	 * @param iteratorUpdate 是否在迭代的时候也更新ttl
	 */
	public AssistTimedMapImpl(Executor cleanExecutor,boolean iteratorUpdate){
		Objects.requireNonNull(cleanExecutor);
		this.lisenterExecutor=cleanExecutor;
		this.iteratorUpdate=iteratorUpdate;
	}
	
	@SuppressWarnings("hiding")
	class AssistTimedEntry<K,V> implements Entry<K, V>{
		/**
		 *缓存对象
		 */
		private final K key;
		
		private V value;
		
		/**
		 * 创建时间
		 */
		private final long createTime=System.currentTimeMillis();
		
		/**
		 * 上次活动
		 */
		private long lastActiveTime;
		
		private TimeOutAssister<K,V> tAssister; 
		private RemoveAssister<K,V> rAssister; 
	
		AssistTimedEntry(K key, V value,TimeOutAssister<K,V> tAssister,RemoveAssister<K,V> rAssister) {
			super();
			this.key = key;
			this.value = value;
			this.lastActiveTime=createTime;
			this.tAssister=tAssister;
			this.rAssister=rAssister;
		}
	
		public long getLastActiveTime() {
			return lastActiveTime;
		}
	
		public void setLastActiveTime(long lastActiveTime) {
			this.lastActiveTime = lastActiveTime;
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
			return tAssister!=null && tAssister.isTimeOut(key, value,createTime,lastActiveTime);
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
				AssistTimedEntry<K,V> other = (AssistTimedEntry<K,V>)o;
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
			return "TimedEntry [createTime=" + createTime + ", lastActiveTime=" + lastActiveTime
					+ ", key=" + key + ", value=" + value + ", tAssister=" + tAssister + ", rAssister=" + rAssister
					+ "]";
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
			for(java.util.Map.Entry<K, AssistTimedMapImpl<K, V>.AssistTimedEntry<K, V>> entry:entryMap.entrySet())
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
					AssistTimedEntry<K, V> entry=removeAndListener(key,true);
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
	protected AssistTimedEntry<K, V> removeAndListener(Object key,final boolean expire)
	{
		final AssistTimedEntry<K, V> entry=entryMap.remove(key);
		try {
			if(entry!=null)
			{//通知被移除
				final RemoveAssister<K, V> listener=entry.rAssister;
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
	public V put(K key, V value,TimeOutAssister<K, V> tAssister,RemoveAssister<K, V> rAssister) {
		if (key == null || value == null) throw new NullPointerException();
		rwLock.writeLock().lock();
		try {
			AssistTimedEntry<K,V> entry=new AssistTimedEntry<K,V>(key, value,tAssister,rAssister);
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
			AssistTimedEntry<K, V> e=entryMap.get(key);
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
		AssistTimedEntry<K,V> value=null;
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
				AssistTimedEntry<K,V> addEntry;
				if(e instanceof AssistTimedEntry)
				{
					addEntry=(AssistTimedEntry<K, V>) e;
				}else
				{
					addEntry=new AssistTimedEntry<K,V>(e.getKey(), e.getValue(),null,null);
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
		Iterator<java.util.Map.Entry<K, AssistTimedMapImpl<K, V>.AssistTimedEntry<K, V>>> it=entryMap.entrySet().iterator();
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}
		@Override
		public Entry<K,V> next() {
			Entry<K, AssistTimedMapImpl<K, V>.AssistTimedEntry<K, V>> value=it.next();
			if(value!=null)
			{
				AssistTimedEntry<K, V> entry=value.getValue();
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
			return AssistTimedMapImpl.this.size();
		}
		
		public boolean isEmpty() {
            return AssistTimedMapImpl.this.isEmpty();
        }

        public void clear() {
        	AssistTimedMapImpl.this.clear();
        }

        public boolean contains(Object k) {
            return AssistTimedMapImpl.this.containsKey(k);
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
			return AssistTimedMapImpl.this.size();
		}
		
		public boolean isEmpty() {
            return AssistTimedMapImpl.this.isEmpty();
        }

        public void clear() {
        	AssistTimedMapImpl.this.clear();
        }

        public boolean contains(Object k) {
            return AssistTimedMapImpl.this.containsKey(k);
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
			return AssistTimedMapImpl.this.size();
		}
		public boolean isEmpty() {
            return AssistTimedMapImpl.this.isEmpty();
        }

        public void clear() {
        	AssistTimedMapImpl.this.clear();
        }
		
		public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>) o;
            Object key = e.getKey();
            return AssistTimedMapImpl.this.containsKey(key);
        }
        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                Object key = e.getKey();
                return  AssistTimedMapImpl.this.remove(key)!= null;
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
}
