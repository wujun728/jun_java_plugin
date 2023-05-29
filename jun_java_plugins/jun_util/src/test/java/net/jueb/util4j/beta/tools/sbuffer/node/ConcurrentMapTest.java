package net.jueb.util4j.beta.tools.sbuffer.node;
  

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jun.plugin.util4j.cache.map.btree.BTreeMap;

import net.jueb.util4j.beta.tools.sbuffer.node.NodeMap4;  

/** 
 * Set -Xms1024M to avoid JVM heap size increase during the test  
 */  
public class ConcurrentMapTest {  
    private static final int THREAD_NUM = 8;  
    private static final int MAP_SIZE = 1000000;  
     
    public static class NMap<K,V> extends NodeMap4<K, V> implements Map<K, V>
    {
    	
		@Override
		public int size() {
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public boolean containsKey(Object key) {
			return false;
		}

		@Override
		public boolean containsValue(Object value) {
			return false;
		}

		@Override
		public V get(Object key) {
			return null;
		}

		@Override
		public V remove(Object key) {
			return null;
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			
		}

		@Override
		public void clear() {
			
		}

		@Override
		public Set<K> keySet() {
			return null;
		}

		@Override
		public Collection<V> values() {
			return null;
		}

		@Override
		public Set<java.util.Map.Entry<K, V>> entrySet() {
			return null;
		}
    }
    
    public static void main(String[] args) throws InterruptedException {  
        List<Integer> list = new ArrayList<Integer>(MAP_SIZE);         
        for (int i = 0; i < MAP_SIZE; i++) {  
            list.add(Integer.valueOf(i));  
        }  
        Collections.shuffle(list);  
                  
        singleThreadMapTest(new HashMap<Integer, Integer>(), list);  
        singleThreadMapTest(new BTreeMap<Integer, Integer>(), list);  
        //singleThreadMapTest(new TreeMap<Integer, Integer>(), list);  
          
        
        
//        concurrentMapTest(Collections.synchronizedMap(new NMap<Integer, Integer>()), list);  
//        concurrentMapTest(Collections.synchronizedMap(new HashMap<Integer, Integer>()), list);  
//        concurrentMapTest(new NMap<Integer, Integer>(), list);  
//        concurrentMapTest(new HashMap<Integer, Integer>(), list);  
//        concurrentMapTest(new ConcurrentHashMap<Integer, Integer>(), list);  
        //concurrentMapTest(Collections.synchronizedSortedMap(new TreeMap<Integer, Integer>()), list);  
        //concurrentMapTest(new ConcurrentSkipListMap<Integer, Integer>(), list);  
    }  
      
    private static void singleThreadMapTest(Map<Integer, Integer> map, List<Integer> l) {  
        //first run to load all to memories and set map initial size          
        mapReadWrite(map, l);  
        mapReadWrite(map, l);  
        map.clear();  
          
        System.out.println("Now start test......");  
        mapReadWrite(map, l.subList(0, (MAP_SIZE/4)));  
        map.clear();  
        mapReadWrite(map, l.subList(0, (MAP_SIZE/4)*2));  
        map.clear();  
        mapReadWrite(map, l.subList(0, (MAP_SIZE/4)*3));  
        map.clear();  
        mapReadWrite(map, l);  
    }  
      
    private static void mapReadWrite(Map<Integer, Integer> map, List<Integer> l) {          
        MapWriter mwHash = new MapWriter(map, l);  
        mwHash.run();          
        MapReader mrHash = new MapReader(map, l.subList(0, (MAP_SIZE/10)));  
        mrHash.run();  
        System.out.println("Map Size = " + map.size());  
    }  
      
    private static void concurrentMapTest(Map<Integer, Integer> map, List<Integer> l) throws InterruptedException {          
        //first run to load all to memories and set map initial size      
        concurrentReadWrite(map, l);  
        map.clear();  
        concurrentReadWrite(map, l);  
        map.clear();  
          
        System.out.println("Now start test......");  
        concurrentReadWrite(map, l);  
        System.out.println("Map Size = " + map.size());  
    }  
      
    private static void concurrentReadWrite(Map<Integer, Integer> map, List<Integer> l)  
            throws InterruptedException {  
        Thread[] writerThreads = new Thread[THREAD_NUM];  
        Thread[] readerThreads = new Thread[THREAD_NUM];  
          
        int mapSize = MAP_SIZE/THREAD_NUM;   
        for (int i = 0; i < THREAD_NUM; ++i) {  
            writerThreads[i] = new Thread(new MapWriter(map, l.subList(mapSize*i, mapSize*(i+1))));  
        }  
        for (int i = 0; i < THREAD_NUM; ++i) {  
            writerThreads[i].start();  
        }  
          
        for (Thread t : writerThreads) {  
            t.join();  
        }  
          
        for (int i = 0; i < THREAD_NUM; ++i) {  
            readerThreads[i] = new Thread(new MapReader(map, l.subList(mapSize*i, mapSize*i+MAP_SIZE/10)));  
        }  
        for (int i = 0; i < THREAD_NUM; ++i) {  
            readerThreads[i].start();  
        }  
          
        for (Thread t : readerThreads) {  
            t.join();  
        }   
    }  
      
    private static class MapWriter implements Runnable {  
        public MapWriter(Map<Integer, Integer> map, List<Integer> l) {  
            this.map = map;  
            this.l = l;  
        }  

        public void run() {  
            long begin = System.currentTimeMillis();  
            for(Integer i : l) {  
                map.put(i, i);  
            }  
            long end = System.currentTimeMillis();  
              
            System.out.println("Write time:" + (end - begin));              
        }  

        private final Map<Integer, Integer> map;  
        private final List<Integer> l;  
    }  
      
    private static class MapReader implements Runnable {  
        public MapReader(Map<Integer, Integer> map, List<Integer> l) {  
            this.map = map;  
            this.l = l;  
        }  

        public void run() {  
            long begin = System.currentTimeMillis();  
            for (Integer i : l) {  
                map.get(i);  
            }  
            long end = System.currentTimeMillis();  
              
            System.out.println("Read time:" + (end - begin));  
        }  

        private final Map<Integer, Integer> map;  
        private final List<Integer> l;  
    }  
}  