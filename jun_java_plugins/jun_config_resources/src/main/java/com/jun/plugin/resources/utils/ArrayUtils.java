package com.jun.plugin.resources.utils;

import java.util.List;

/**
 * Created by Hong on 2017/12/27.
 */
public final class ArrayUtils {

    private ArrayUtils() {

    }

    public static <E> String toString(E[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (E e : array) {
            builder.append(",").append(e);
        }
        builder.deleteCharAt(0);
        return builder.toString();
    }

    public static <E> String toString(List<E> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (E e : list) {
            builder.append(",").append(e);
        }
        builder.deleteCharAt(0);
        return builder.toString();
    }
}
