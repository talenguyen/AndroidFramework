package com.talenguyen.androidframework.module.parse;

import java.lang.reflect.Field;

import android.text.TextUtils;

import com.parse.ParseObject;

public class ParseUtils {

	static final String TAG = ParseUtils.class.getSimpleName();

	public static ParseObject format(Object object)
			throws IllegalArgumentException, IllegalAccessException {
		if (object == null) {
			return null;
		}

		final Class<?> clazz = object.getClass();
		final Field[] fields = clazz.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return null;
		}

		final ParseObject parseObject = new ParseObject(clazz.getSimpleName());
		for (Field field : fields) {
			field.setAccessible(true);
			final String type = field.getType().toString();
			if (type.equals("int") || type.contains("java.lang.Integer")) {
				parseObject.put(field.getName(), (Integer) field.get(object));
			} else if (type.equals("boolean")
					|| type.contains("java.lang.Boolean")) {
				parseObject.put(field.getName(), (Boolean) field.get(object));
			} else if (type.equals("long") || type.contains("java.lang.Long")) {
				parseObject.put(field.getName(), (Long) field.get(object));
			} else if (type.equals("float") || type.contains("java.lang.Float")) {
				parseObject.put(field.getName(), (Float) field.get(object));
			} else if (type.equals("double")
					|| type.contains("java.lang.Double")) {
				parseObject.put(field.getName(), (Double) field.get(object));
			} else if (type.contains("java.lang.String")) {
				parseObject.put(field.getName(), (String) field.get(object));
			}
		}

		return parseObject;
	}
	
	public static<T> T parse(ParseObject parseObject, Class<T> destClass) throws InstantiationException, IllegalAccessException {
		if (parseObject == null) {
			return null;
		}

		T result = destClass.newInstance();
		
		final Class<?> clazz = destClass.getClass();
		final Field[] fields = clazz.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return result;
		}

		for (Field field : fields) {
			field.setAccessible(true);
			final String type = field.getType().toString();
			final String value = parseObject.getString(field.getName());
			if (TextUtils.isEmpty(value)) {
				continue;
			}
			if (type.equals("int") || type.contains("java.lang.Integer")) {
				field.setInt(result, Integer.parseInt(value));
			} else if (type.equals("boolean")
					|| type.contains("java.lang.Boolean")) {
				field.setBoolean(result, value.equals("1"));
			} else if (type.equals("long") || type.contains("java.lang.Long")) {
				field.setLong(result, Long.parseLong(value));
			} else if (type.equals("float") || type.contains("java.lang.Float")) {
				field.setFloat(result, Float.parseFloat(value));
			} else if (type.equals("double")
					|| type.contains("java.lang.Double")) {
				field.setDouble(result, Double.parseDouble(value));
			} else if (type.contains("java.lang.String")) {
				field.set(result, value);
			}
		}

		return result;
	}

}
