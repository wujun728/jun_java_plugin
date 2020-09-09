package com.rann.datastructure.jcollections;

/**
 * 实现自己的List接口
 */
public interface IList<E> {
    /**
     * 列表元素总个数
     * @return
     */
    int size();

    /**
     * 列表个数为0时返回true
     * @return
     */
    boolean isEmpty();

    /**
     * 判断是否存在某个元素
     * @param o
     * @return
     */
    boolean contains(Object o);

    /**
     * 添加元素
     * @param element
     * @return
     */
    boolean add(E element);

    /**
     * 指定位置添加
     * @param index
     * @param element
     */
    void add(int index, E element);

    /**
     * 获取指定位置元素
     * @param index
     * @return
     */
    E get(int index);

    /**
     * 替换指定位置元素
     * @param index
     * @param element
     * @return
     */
    E set(int index, E element);

    /**
     * 删除元素
     * @param o
     * @return
     */
    boolean remove(Object o);

    /**
     * 删除指定位置元素
     * @param index
     * @return
     */
    E remove(int index);

    /**
     * 第一次出现的索引
     * @param o
     * @return
     */
    int indexOf(Object o);

    /**
     * 最后出现的索引
     * @param o
     * @return
     */
    int lastIndexOf(Object o);

    /**
     * 清空列表
     * remove all elements from the list
     */
    void clear();
}
