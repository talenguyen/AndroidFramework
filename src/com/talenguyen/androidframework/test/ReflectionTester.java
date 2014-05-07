package com.talenguyen.androidframework.test;

import java.lang.reflect.Field;

public class ReflectionTester {

	public static void main(String[] args) {
		Class<?> clazz = PrimaryType.class;
		final Field[] fields = clazz.getFields();
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				System.out.println("Field: " + fields[i].getName());
			}
		}
		
		System.out.println("DeclaredFields====");
		PrimaryType primaryType = new PrimaryType();
		final Field[] declaredFields = clazz.getDeclaredFields();
		if (declaredFields != null && declaredFields.length > 0) {
			for (int i = 0; i < declaredFields.length; i++) {
				final Field field = declaredFields[i];
				field.setAccessible(true);
				if (field.getType().toString().contains("java.lang.Boolean")) {
					try {
						field.set(primaryType, true);
						System.out.println("Boolean " + (Boolean) field.get(primaryType));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Field's type: " + field.getType());
				System.out.println("Field: " + field.getName());
			}
		}
		System.out.println("Declared fields");
		printDeclaredFields(PrimaryType.class);
	}
	
	private static void printDeclaredFields(Class<?> clazz) {
		final Field[] declaredFields = clazz.getDeclaredFields();
		if (declaredFields != null && declaredFields.length > 0) {
			for (Field field : declaredFields) {
				System.out.println(field.getName());
			}
		}
	}
}
