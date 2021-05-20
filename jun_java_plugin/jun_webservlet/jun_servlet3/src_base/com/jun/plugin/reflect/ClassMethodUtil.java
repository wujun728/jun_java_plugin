package com.jun.plugin.reflect;


 

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ClassMethodUtil {
        
        /**
         * return object's setter.
         *
         * @param object
         * @return
         */
        public static Map<String, Method> getSetMethodMap(Object object) {
                return getSetMethodMap(object.getClass());
        }
        
        /**
         * return object's getter.
         *
         * @param object
         * @return
         */
        public static Map<String, Method> getGetMethodMap(Object object) {
                return getGetMethodMap(object.getClass());
        }

        /**
         * return class's setter.
         *
         * @param clazz
         * @return
         */
        public static Map<String, Method> getSetMethodMap(Class<?> clazz) {
                return getMethodMap(clazz).get(setPrefix);
        }

        /**
         * return class's getter.
         *
         * @param clazz
         * @return
         */
        public static Map<String, Method> getGetMethodMap(Class<?> clazz) {
                return getMethodMap(clazz).get(getPrefix);
        }
        
        /**
         * clear static map.
         */
        public static void clearClassMethodMap() {
                classMethodMap.clear();
        }

        /**
         * return class's setter & getter from static map.
         *
         * @param clazz
         * @return
         */
        private static Map<String, Map<String, Method>> getMethodMap(Class<?> clazz) {
                cleanClassMethodMap();
                String classname = clazz.getName();
                if (!classMethodMap.containsKey(classname)) {
                        classMethodMap.put(classname, getMethodMapByClass(clazz));
                }
                return classMethodMap.get(classname);
        }

        /* return class's setter & getter */
        private static Map<String, Map<String, Method>> getMethodMapByClass(Class<?> clazz) {
                Method[] methods = clazz.getMethods();
                int initialCapacity = methods.length / 2;
                Map<String, Method> setMethodMap = new HashMap<String, Method>(initialCapacity);
                Map<String, Method> getMethodMap = new HashMap<String, Method>(initialCapacity + 1);
                for (Method method : methods) {
                        String name = method.getName();
                        if (name.startsWith(setPrefix) && !setPrefix.equals(name)) {
                                if (isStandardSetMethod(method)) {
                                        setMethodMap.put(toStandardFiledName(name), method);
                                }
                        } else if (name.startsWith(getPrefix) && !getPrefix.equals(name)) {
                                if (isStandardGetMethod(method)) {
                                        getMethodMap.put(toStandardFiledName(name), method);
                                }
                        }
                }
                Map<String, Map<String, Method>> methodmap = new HashMap<String, Map<String, Method>>(2);
                methodmap.put(setPrefix, setMethodMap);
                methodmap.put(getPrefix, getMethodMap);
                return methodmap;
        }

        /* return true when method is standard setter */
        private static boolean isStandardSetMethod(Method method) {
                return method.getGenericParameterTypes().length == 1;
        }

        /* return true when method is standard setter */
        private static boolean isStandardGetMethod(Method method) {
                return method.getGenericParameterTypes().length == 0;
        }

        /* return standard setter/getter fieldName */
        private static String toStandardFiledName(String name) {
                String fieldname = name.substring(3);
                return fieldname.substring(0, 1).toLowerCase() + fieldname.substring(1);
        }

        /* 清理bean的methodMap策略 */
        private static void cleanClassMethodMap() {
                ;
        }

        /* setter prefix */
        private final static String setPrefix = "set";

        /* getter prefix */
        private final static String getPrefix = "get";

        /* className getter/setter/prefix fieldName Method */
        private final static Map<String, Map<String, Map<String, Method>>> classMethodMap = new HashMap<String, Map<String, Map<String, Method>>>();

}