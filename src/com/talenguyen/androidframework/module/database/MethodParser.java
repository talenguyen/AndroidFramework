package com.talenguyen.androidframework.module.database;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/22/13
 * Time: 12:19 AM
 */
class MethodParser {
    String getterName;
    String getterMethod;
    String returnType;

    public static MethodParser parse(Method method) {
        if (method == null) {
            return null;
        }
        final String methodName = method.getName();
        int index = -1;
        if (!methodName.equals("getClass")) {
            if (methodName.startsWith("get")) {
                // for getter e.g. getA().
                index = 3;
            } else if (methodName.startsWith("is")) {
                // for boolean getter e.g. isX().
                index = 2;
            }
        }
        if (index != -1) {
            final String getterName = methodName.substring(index);
            return new MethodParser(getterName, methodName, method.getReturnType().getName());
        }

        return null;
    }

    public MethodParser(String getterName, String getterMethod, String returnType) {
        this.getterName = getterName;
        this.getterMethod = getterMethod;
        this.returnType = returnType;
    }
}
