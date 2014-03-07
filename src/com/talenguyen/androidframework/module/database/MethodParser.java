package com.talenguyen.androidframework.module.database;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/22/13
 * Time: 12:19 AM
 */
public class MethodParser {
    String getterName;
    String returnType;

    public static MethodParser parse(Method method) {
        if (method == null) {
            return null;
        }
        final String name = method.getName();
        if (name.startsWith("get") && !name.equals("getClass")) {
            final String getName = name.substring(3);
            return new MethodParser(getName, method.getReturnType().getName());
        }

        return null;
    }

    public MethodParser(String getterName, String returnType) {
        this.getterName = getterName;
        this.returnType = returnType;
    }
}
