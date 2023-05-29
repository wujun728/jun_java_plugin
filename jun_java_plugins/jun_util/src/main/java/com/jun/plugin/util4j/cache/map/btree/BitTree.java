package com.jun.plugin.util4j.cache.map.btree;

public interface BitTree<V> {

	/**
	 * 存储数据
	 * @param bitNumber
	 * @param value
	 * @return
	 */
	public V write(int bitNumber,V value);
	/**
	 * 读取数据
	 * @param key
	 * @return
	 */
	public V read(int bitNumber);
	
    void forEach(BitConsumer<V> consumer);
    
    @FunctionalInterface
    public static interface BitConsumer<V>{
    	void accept(int bitNumber,V value);
    }
}
