package com.talenguyen.androidframework.module.database;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GIANG
 * Date: 12/22/13
 * Time: 12:24 AM
 */
class ClassParser {

    List<MethodParser> methods;
    String[] getterFields;

    public ClassParser(List<MethodParser> methods, String[] getterFields) {
        this.methods = methods;
        this.getterFields = getterFields;
    }

    public static ClassParser parse(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        final Method[] methods = clazz.getMethods();
        final ArrayList<MethodParser> methodParsers = new ArrayList<MethodParser>();
        final ArrayList<String> getterFields = new ArrayList<String>();
        for (int i = 0; i < methods.length; i++) {
            final MethodParser methodParser = MethodParser.parse(methods[i]);
            if (methodParser != null) {
                methodParsers.add(methodParser);
                getterFields.add(methodParser.getterName);
            }
        }
        return new ClassParser(methodParsers, getterFields.toArray(new String[]{}));
    }
}
