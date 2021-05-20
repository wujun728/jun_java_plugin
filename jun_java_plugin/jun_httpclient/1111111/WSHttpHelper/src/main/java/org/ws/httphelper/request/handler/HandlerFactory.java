package org.ws.httphelper.request.handler;

import org.ws.httphelper.exception.WSException;

import java.util.HashMap;
import java.util.Map;

public class HandlerFactory {
    private static Map<Object, Object> handlersMap = new HashMap<Object, Object>();

    private HandlerFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T finadHandler(Class<T> clazz) throws WSException {
        if (handlersMap.containsKey(clazz)) {
            return (T) handlersMap.get(clazz);
        } else {
            try {
                T handler = clazz.newInstance();
                handlersMap.put(clazz, handler);
                return handler;
            } catch (Exception e) {
                throw new WSException(e);
            }
        }
    }

    public static <T> T finadHandler(Class<T> panentClass, String className) throws WSException {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new WSException(e);
        }
        if (handlersMap.containsKey(clazz)) {
            return (T) handlersMap.get(clazz);
        } else {
            try {
                T handler = (T) clazz.newInstance();
                handlersMap.put(clazz, handler);
                return handler;
            } catch (Exception e) {
                throw new WSException(e);
            }
        }
    }

    public static boolean remove(Class<?> clazz, Object handle) {
        if (handlersMap.containsKey(clazz)) {
            handlersMap.remove(handle);
            return true;
        }
        return false;
    }


}
