Map系列集合总结使用
https://www.cnblogs.com/skywang12345/p/3311126.html#a1

java 核心集合
https://segmentfault.com/a/1190000014403696


Collection
        |--------List
                    |----------LinkedList
                    |----------ArrayList
                    |----------Vector
                                     |-----Stack
        |--------Set
                    |----------HashSet
                    |----------TreeSet
Map
        |---------HashMap  HashTable   concurrentHashMap
        |---------TreeMap


16个数组长度。0.75 。 (n *4 )%3 >0 ? (n *4 /3 ) : (n *4 /3 )+1

16 * 0.75 = 12    >  new  16 + 8  = 24

