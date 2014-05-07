package com.talenguyen.androidframework.test;

import com.parse.ParseObject;
import com.talenguyen.androidframework.module.parse.ParseUtils;

public class ParseUtilTester {

	public static void main(String[] args) {
		
		ParseObject parseObject = new ParseObject(PrimaryType.class.getSimpleName());
		parseObject.put("intValue", 1);
		parseObject.put("bigIntValue", 10);
		parseObject.put("booleanValue", true);
		parseObject.put("bigBooleanValue", true);
		parseObject.put("longValue", 2);
		parseObject.put("bigLongValue", 20);
		parseObject.put("floatValue", 3.1f);
		parseObject.put("bigFloatValue", 30.1f);
		parseObject.put("doubleValue", 4.1d);
		parseObject.put("bigDoubleValue", 40.1d);
		parseObject.put("stringValue", "String value");
		
		PrimaryType primaryType = null;
		try {
			primaryType = ParseUtils.parse(parseObject, PrimaryType.class);
			System.out.println("PrimaryType: " + primaryType.toString());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		ParseObject parseObject2 = null;
		if (primaryType != null) {
			try {
				parseObject2 = ParseUtils.format(primaryType);
				PrimaryType primaryType2 = ParseUtils.parse(parseObject2, PrimaryType.class);
				System.out.println("PrimaryType2: " + primaryType2.toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		
	}
}
