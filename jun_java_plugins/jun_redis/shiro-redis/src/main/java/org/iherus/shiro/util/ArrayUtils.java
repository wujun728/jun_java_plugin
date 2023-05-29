/**
 * Copyright (c) 2016-~, Bosco.Liao (bosco_liao@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.iherus.shiro.util;

import java.lang.reflect.Array;

/**
 * <p>数组工具类</p>
 * <p>Description:提供常用的数组克隆、数组合并和截取子数组等功能</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月12日-下午8:30:59
 */
public final class ArrayUtils {

	public static final int[] EMPTY_INT_ARRAY = new int[0];

	public static final char[] EMPTY_CHAR_ARRAY = new char[0];

	public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	
	private ArrayUtils() {

	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 * </p>
	 */
	public static int[] clone(final int[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 * </p>
	 */
	public static char[] clone(final char[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 * </p>
	 */
	public static byte[] clone(final byte[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Merges all the elements of the given arrays into a new array.
	 * </p>
	 */
	public static char[] mergeAll(final char[] array1, final char... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final char[] joinedArray = new char[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Merges all the elements of the given arrays into a new array.
	 * </p>
	 */
	public static byte[] mergeAll(final byte[] array1, final byte... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final byte[] joinedArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Merges all the elements of the given arrays into a new array.
	 * </p>
	 */
	public static int[] mergeAll(final int[] array1, final int... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final int[] joinedArray = new int[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Copies the given array and merges the given element at the end of the new
	 * array.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] merge(final T[] array, final T element) {
		Class<?> type;
		if (array != null) {
			type = array.getClass().getComponentType();
		} else if (element != null) {
			type = element.getClass();
		} else {
			throw new IllegalArgumentException("Arguments cannot both be null");
		}
		final T[] newArray = (T[]) expandCopy(array, type);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Produces a new {@code int} array containing the elements between the
	 * start and end indices.
	 * </p>
	 */
	public static int[] subarray(final int[] array, int startIndexInclusive, int endIndexExclusive) {
		if (array == null) {
			return null;
		}
		if (startIndexInclusive < 0) {
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length) {
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0) {
			return EMPTY_INT_ARRAY;
		}
		final int[] subarray = new int[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code char} array containing the elements between the
	 * start and end indices.
	 * </p>
	 */
	public static char[] subarray(final char[] array, int startIndexInclusive, int endIndexExclusive) {
		if (array == null) {
			return null;
		}
		if (startIndexInclusive < 0) {
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length) {
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0) {
			return EMPTY_CHAR_ARRAY;
		}
		final char[] subarray = new char[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new {@code byte} array containing the elements between the
	 * start and end indices.
	 * </p>
	 */
	public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
		if (array == null) {
			return null;
		}
		if (startIndexInclusive < 0) {
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length) {
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		if (newSize <= 0) {
			return EMPTY_BYTE_ARRAY;
		}
		final byte[] subarray = new byte[newSize];
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * <p>
	 * Produces a new array containing the elements between the start and end
	 * indices.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
		if (array == null) {
			return null;
		}
		if (startIndexInclusive < 0) {
			startIndexInclusive = 0;
		}
		if (endIndexExclusive > array.length) {
			endIndexExclusive = array.length;
		}
		final int newSize = endIndexExclusive - startIndexInclusive;
		final Class<?> type = array.getClass().getComponentType();
		if (newSize <= 0) {
			final T[] emptyArray = (T[]) Array.newInstance(type, 0);
			return emptyArray;
		}
		final T[] subarray = (T[]) Array.newInstance(type, newSize);
		System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
		return subarray;
	}

	/**
	 * Returns a copy of the given array of size 1 greater than the argument.
	 * The last value of the array is left to the default value.
	 */
	private static Object expandCopy(final Object array, final Class<?> newArrayComponentType) {
		if (array != null) {
			final int arrayLength = Array.getLength(array);
			final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
			System.arraycopy(array, 0, newArray, 0, arrayLength);
			return newArray;
		}
		return Array.newInstance(newArrayComponentType, 1);
	}

}
