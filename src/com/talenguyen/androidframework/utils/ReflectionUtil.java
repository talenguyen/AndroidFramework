package com.talenguyen.androidframework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/22/13
 * Time: 1:48 AM
 */
public class ReflectionUtil {

    /**
     * Involve method ob an Object by using reflection.
     *
     * @param target     The object to involve method
     * @param methodName The method name
     * @param params     The input params for the method
     * @return The result from return of method or null if Exception occur.
     */
    public static Object involveMethod(Object target, String methodName, Object... params) {
        Class<?>[] paramsClass = null;
        if (params != null && params.length > 0) {
            paramsClass = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                final Object param = params[i];
                if (param == null) {
                    continue;
                }
                paramsClass[i] = getPrimaryTypeClass(param.getClass());
            }
        }
        try {
            final Method method = target.getClass().getMethod(methodName, paramsClass);
            return method.invoke(target, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Involve constructor of a class to get new instance of that class
     *
     * @param clazz  The class to involve the Constructor
     * @param params The input params for the Constructor.
     * @param <T>    Type of that clazz instance.
     * @return the new instance of that class if succeed or null otherwise.
     */
    public static <T> T newInstance(Class<? extends T> clazz, Object... params) {
        try {

            if (params == null || params.length == 0) {
                return clazz.newInstance();
            }

            final Class<?>[] paramsClass = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                paramsClass[i] = getPrimaryTypeClass(params[i].getClass());
            }

            final Constructor<? extends T> constructor = clazz.getConstructor(paramsClass);
            return constructor.newInstance(params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Class<?> getPrimaryTypeClass(Class<?> clazz) {
        final String className = clazz.getName();
        if (className.equals("java.lang.Integer")) {
            return int.class;
        } else if (className.equals("java.lang.Long")) {
            return long.class;
        } else if (className.equals("java.lang.Float")) {
            return float.class;
        } else if (className.equals("java.lang.Double")) {
            return double.class;
        } else if (className.equals("java.lang.Boolean")) {
            return boolean.class;
        } else if (className.equals("java.lang.String")) {
            return String.class;
        }
        return clazz;
    }
}
