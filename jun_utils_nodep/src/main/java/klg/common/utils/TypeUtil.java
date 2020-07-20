package klg.common.utils;

/**
 * The TypeUtil class static methods for inspecting complex java types.
 * The typeToString() method is used to dump the contents of a passed object 
 * of any type (or collection) to a String.  This can be very useful for debugging code that
 * manipulates complex structures.
 *
 *
 * @version $Revision : 1.2.6.4 $
 */

import java.util.*;
import java.lang.reflect.*;

public class TypeUtil {

	/**
	 * Returns a string holding the contents of the passed object,
	 * 
	 * @param scope
	 *            String
	 * @param parentObject
	 *            Object
	 * @param visitedObjs
	 *            List
	 * @return String
	 */
	private static String complexTypeToString(String scope, Object parentObject, List visitedObjs) {
		StringBuffer buffer = new StringBuffer("");
		try {
			//
			// Ok, now we need to reflect into the object and add its child
			// nodes...
			//

			Class cl = parentObject.getClass();
			while (cl != null) {

				processFields(cl.getDeclaredFields(), scope, parentObject, buffer, visitedObjs);

				cl = cl.getSuperclass();
			}
		} catch (IllegalAccessException iae) {
			buffer.append(iae.toString());
		}

		return (buffer.toString());
	}

	/**
	 * Method processFields
	 * 
	 * @param fields
	 *            Field[]
	 * @param scope
	 *            String
	 * @param parentObject
	 *            Object
	 * @param buffer
	 *            StringBuffer
	 * @param visitedObjs
	 *            List
	 * @throws IllegalAccessException
	 */
	private static void processFields(Field[] fields, String scope, Object parentObject,
			StringBuffer buffer, List visitedObjs) throws IllegalAccessException {
		for (int i = 0; i < fields.length; i++) {
			//
			// Disregard certain fields for IDL structures
			//
			if (fields[i].getName().equals("__discriminator")
					|| fields[i].getName().equals("__uninitialized")) {
				continue;
			}

			//
			// This allows us to see non-public fields. We might need to deal
			// with some
			// SecurityManager issues here once it is outside of VAJ...
			//
			fields[i].setAccessible(true);
			if (Modifier.isStatic(fields[i].getModifiers())) {
				//
				// Ignore all static members. The classes that this dehydrator
				// is
				// meant to handle are simple data objects, so static members
				// have no
				// bearing....
				//
			} else {
				buffer.append(typeToString(scope + "." + fields[i].getName(), fields[i]
						.get(parentObject), visitedObjs));
			}
		}
	}

	/**
	 * Method isCollectionType
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public static boolean isCollectionType(Object obj) {

		return (obj.getClass().isArray() || (obj instanceof Collection)
				|| (obj instanceof Hashtable) || (obj instanceof HashMap)
				|| (obj instanceof HashSet) || (obj instanceof List) || (obj instanceof AbstractMap));
	}

	/**
	 * Method isComplexType
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public static boolean isComplexType(Object obj) {

		if (obj instanceof Boolean || obj instanceof Short || obj instanceof Byte
				|| obj instanceof Integer || obj instanceof Long || obj instanceof Float
				|| obj instanceof Character || obj instanceof Double || obj instanceof String) {

			return false;
		} else {
			Class objectClass = obj.getClass();

			if (objectClass == boolean.class || objectClass == Boolean.class
					|| objectClass == short.class || objectClass == Short.class
					|| objectClass == byte.class || objectClass == Byte.class
					|| objectClass == int.class || objectClass == Integer.class
					|| objectClass == long.class || objectClass == Long.class
					|| objectClass == float.class || objectClass == Float.class
					|| objectClass == char.class || objectClass == Character.class
					|| objectClass == double.class || objectClass == Double.class
					|| objectClass == String.class) {
				return false;
			}

			else {
				return true;
			}
		}
	}

	/**
	 * Returns a string holding the contents of the passed object,
	 * 
	 * @param scope
	 *            String
	 * @param obj
	 *            Object
	 * @param visitedObjs
	 *            List
	 * @return String
	 */
	private static String collectionTypeToString(String scope, Object obj, List visitedObjs) {
		StringBuffer buffer = new StringBuffer("");
		if (obj.getClass().isArray()) {
			if (Array.getLength(obj) > 0) {
				for (int j = 0; j < Array.getLength(obj); j++) {
					Object x = Array.get(obj, j);
					buffer.append(typeToString(scope + "[" + j + "]", x, visitedObjs));
				}
			} else {
				buffer.append(scope + "[]: empty\r\n");
			}
		} else {
			boolean isCollection = (obj instanceof Collection);
			boolean isHashTable = (obj instanceof Hashtable);
			boolean isHashMap = (obj instanceof HashMap);
			boolean isHashSet = (obj instanceof HashSet);
			boolean isAbstractMap = (obj instanceof AbstractMap);
			boolean isMap = isAbstractMap || isHashMap || isHashTable;
			if (isMap) {
				Set keySet = ((Map) obj).keySet();
				Iterator iterator = keySet.iterator();
				int size = keySet.size();
				if (size > 0) {
					for (int j = 0; iterator.hasNext(); j++) {
						Object key = iterator.next();
						Object x = ((Map) obj).get(key);
						buffer.append(typeToString(scope + "[" + key + "]", x, visitedObjs));
					}
				} else {
					buffer.append(scope + "[]: empty\r\n");
				}
			} else if (isCollection || isHashSet) {
				Iterator iterator = null;
				int size = 0;
				if (obj != null) {
					if (isCollection) {
						iterator = ((Collection) obj).iterator();
						size = ((Collection) obj).size();
					} else if (isHashSet) {
						iterator = ((HashSet) obj).iterator();
						size = ((HashSet) obj).size();
					}
					if (size > 0) {
						for (int j = 0; iterator.hasNext(); j++) {
							Object x = iterator.next();
							buffer.append(typeToString(scope + "[" + j + "]", x, visitedObjs));
						}
					} else {
						buffer.append(scope + "[]: empty\r\n");
					}
				} else {
					//
					// theObject is null
					buffer.append(scope + "[]: null\r\n");
				}
			}
		}
		return (buffer.toString());
	}

	/**
	 * Method typeToString
	 * 
	 * @param scope
	 *            String
	 * @param obj
	 *            Object
	 * @param visitedObjs
	 *            List
	 * @return String
	 */
	private static String typeToString(String scope, Object obj, List visitedObjs) {

		if (obj == null) {
			return (scope + ": null\r\n");
		} else if (isCollectionType(obj)) {
			return collectionTypeToString(scope, obj, visitedObjs);
		} else if (isComplexType(obj)) {
			if (!visitedObjs.contains(obj)) {
				visitedObjs.add(obj);
				return complexTypeToString(scope, obj, visitedObjs);
			} else {
				return (scope + ": <already visited>\r\n");
			}
		} else {
			return (scope + ": " + obj.toString() + "\r\n");
		}
	}

	/**
	 * The typeToString() method is used to dump the contents of a passed object
	 * of any type (or collection) to a String. This can be very useful for
	 * debugging code that manipulates complex structures.
	 * 
	 * @param scope
	 * @param obj
	 * 
	 * @return String
	 * 
	 */

	public static String typeToString(String scope, Object obj) {

		if (obj == null) {
			return (scope + ": null\r\n");
		} else if (isCollectionType(obj)) {
			return collectionTypeToString(scope, obj, new ArrayList());
		} else if (isComplexType(obj)) {
			return complexTypeToString(scope, obj, new ArrayList());
		} else {
			return (scope + ": " + obj.toString() + "\r\n");
		}
	}

	public static void print(String scope, Object obj) {
		System.out.println(typeToString(scope, obj));
	}

}
