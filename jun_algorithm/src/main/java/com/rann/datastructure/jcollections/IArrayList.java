package com.rann.datastructure.jcollections;

import java.util.Arrays;

/**
 * 实现自己的ArrayList
 *
 * @param <E>
 * @author hztaoran
 */
public class IArrayList<E> implements IList<E> {

    // 默认装载因子
    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    private Object[] elementData;

    public IArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public IArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("capacity < 0");
        }
        elementData = new Object[initialCapacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * 末尾添加
     * @param element
     * @return
     */
    @Override
    public boolean add(E element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;

        return true;
    }

    /**
     * 指定索引添加时元素后移
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
    }

    @Override
    public E get(int index) {
        rangeCheck(index);

        return (E)elementData[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = (E)elementData[index];
        elementData[index] = element;

        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
        if (null == o) {
            for (int i = 0; i < size; i++) {
                if (null == elementData[i]) {
                    fastRemove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    fastRemove(i);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E oldValue = (E)elementData[index];
        fastRemove(index);

        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (null == elementData[i]) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (null == elementData[i]) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * 扩容
     * @param minCapacity
     */
    private void ensureCapacity(int minCapacity) {
        int oldCapacity = elementData.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (3 * oldCapacity) / 2 + 1;
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    /**
     * 判断指定索引
     * @param index
     */
    private void rangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index out of bounds, index");
        }
    }

    /**
     * 删除元素的移动
     * @param index
     */
    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
    }
}