package com.bing.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import com.google.common.primitives.Primitives;

/**
 * @author shizhongtao
 * 
 * @date 2016-2-29 Description:
 * 
 */

public class ReflectDependencyFactory {
	/**
	 * 根据参数数组，构造实例
	 * @param type object类型
	 * @param args 参数
	 * @return
	 */
	
	public static Object newInstance(final Class type,
			final Object[] args) {
		if (args != null && args.length > 50) {
			throw new IllegalArgumentException(
					"More than 50 arguments are not supported");
		}
		Constructor bestMatchingCtor = null;
		final ArrayList matchingDependencies = new ArrayList();
		List possibleMatchingDependencies = null;
		long usedDeps = 0;
		long possibleUsedDeps = 0;

		if (args != null && args.length > 0) {
			// sort available ctors according their arity, desc
			final Constructor[] ctors = type.getConstructors();
			if (ctors.length > 1) {
				Arrays.sort(ctors, new Comparator() {
					public int compare(final Object o1, final Object o2) {
						return ((Constructor) o2).getParameterTypes().length
								- ((Constructor) o1).getParameterTypes().length;
					}
				});
			}

			final TypedValue[] typedDependencies = new TypedValue[args.length];
			for (int i = 0; i < args.length; i++) {
				Object dependency = args[i];
				Class depType = dependency.getClass();
				if (depType.isPrimitive()) {
					depType = Primitives.wrap(depType);
				} 
				//传入之前，没有考虑null值的转换。

				typedDependencies[i] = new TypedValue(depType, dependency);
			}

			Constructor possibleCtor = null;
			int arity = Integer.MAX_VALUE;
			for (int i = 0; bestMatchingCtor == null && i < ctors.length; i++) {
				final Constructor constructor = ctors[i];
				final Class[] parameterTypes = constructor.getParameterTypes();
				if (parameterTypes.length > args.length) {
					continue;
				} else if (parameterTypes.length == 0) {
					if (possibleCtor == null) {
						bestMatchingCtor = constructor;
					}
					break;
				}
				if (arity > parameterTypes.length) {
					if (possibleCtor != null) {
						continue;
					}
					arity = parameterTypes.length;
				}

				for (int j = 0; j < parameterTypes.length; j++) {
					if (parameterTypes[j].isPrimitive()) {
						parameterTypes[j] = Primitives.wrap(parameterTypes[j]);
					}
				}

				// first approach: test the ctor params against the dependencies
				// in the sequence
				// of the parameter declaration
				matchingDependencies.clear();
				usedDeps = 0;
				
				for (int j = 0, k = 0; j < parameterTypes.length
						&& parameterTypes.length + k - j <= typedDependencies.length; k++) {
					//确保有一个参数能对上
					if (parameterTypes[j].isAssignableFrom(typedDependencies[k].type)) {
						matchingDependencies.add(typedDependencies[k].value);
						usedDeps |= 1L << k;
						if (++j == parameterTypes.length) {
							bestMatchingCtor = constructor;
							break;
						}
					}
				}

				if (bestMatchingCtor == null) {
					boolean possible = true; // assumption

					// try to match all dependencies in the sequence of the
					// parameter
					// declaration
					final TypedValue[] deps = new TypedValue[typedDependencies.length];
					System.arraycopy(typedDependencies, 0, deps, 0, deps.length);
					matchingDependencies.clear();
					usedDeps = 0;
					for (int j = 0; j < parameterTypes.length; j++) {
						int assignable = -1;
						for (int k = 0; k < deps.length; k++) {
							if (deps[k] == null) {
								continue;
							}
							if (deps[k].type == parameterTypes[j]) {
								assignable = k;
								// optimal match
								break;
							} else if (parameterTypes[j]
									.isAssignableFrom(deps[k].type)) {
								// use most specific type
								if (assignable < 0
										|| (deps[assignable].type != deps[k].type && deps[assignable].type
												.isAssignableFrom(deps[k].type))) {
									assignable = k;
								}
							}
						}

						if (assignable >= 0) {
							matchingDependencies.add(deps[assignable].value);
							usedDeps |= 1L << assignable;
							deps[assignable] = null; // do not match same dep
														// twice
						} else {
							possible = false;
							break;
						}
					}

					if (possible) {
						// the smaller the value, the smaller the indices in the
						// deps array
						if (possibleCtor != null
								&& usedDeps >= possibleUsedDeps) {
							continue;
						}
						possibleCtor = constructor;
						possibleMatchingDependencies = (List) matchingDependencies
								.clone();
						possibleUsedDeps = usedDeps;
					}
				}
			}

			if (bestMatchingCtor == null) {
				if (possibleCtor == null) {
					usedDeps = 0;
					throw new IllegalArgumentException(
							"Cannot construct "
									+ type.getName()
									+ ", none of the dependencies match any constructor's parameters");
				} else {
					bestMatchingCtor = possibleCtor;
					matchingDependencies.clear();
					matchingDependencies.addAll(possibleMatchingDependencies);
					usedDeps = possibleUsedDeps;
				}
			}
		}

		try {
			final Object instance;
			if (bestMatchingCtor == null) {
				instance = type.newInstance();
			} else {
				instance = bestMatchingCtor.newInstance(matchingDependencies
						.toArray());
			}
			
			return instance;
		} catch (final InstantiationException e) {
			throw new IllegalArgumentException("Cannot construct "
					+ type.getName(), e);
		} catch (final IllegalAccessException e) {
			throw new IllegalArgumentException("Cannot construct "
					+ type.getName(), e);
		} catch (final InvocationTargetException e) {
			throw new IllegalArgumentException("Cannot construct "
					+ type.getName(), e);
		} catch (final SecurityException e) {
			throw new IllegalArgumentException("Cannot construct "
					+ type.getName(), e);
		} catch (final ExceptionInInitializerError e) {
			throw new IllegalArgumentException("Cannot construct "
					+ type.getName(), e);
		}
	}

	private static class TypedValue {
		final Class type;
		final Object value;

		public TypedValue(final Class type, final Object value) {
			super();
			this.type = type;
			this.value = value;
		}

		public String toString() {
			return type.getName() + ":" + value;
		}
	}
}
