package com.talenguyen.androidframework.test;

public class PrimaryType {

	public int intValue = 1;
	public Integer bigIntValue = 10;
	public boolean booleanValue= true;
	public Boolean bigBooleanValue = true;
	public long longValue = 2;
	public Long bigLongValue = 20l;
	private float floatValue= 3.3f;
	private Float bigFloatValue = 30.3f;
	private double doubleValue  = 4.4d;
	private Double bigDoubleValue = 40.4d;
	private String stringValue = "string";

	@Override
	public String toString() {
		return "intValue:" + intValue 
				+ ", bigIntValue:" + bigIntValue
				+ ", booleanValue:" + booleanValue 
				+ ", bigBooleanValue:" + bigBooleanValue 
				+ ", bigIntValue:" + bigIntValue 
				+ ", longValue:" + longValue 
				+  ", bigLongValue:" + bigLongValue
				+ ", floatValue:" + floatValue
				+ ", bigFloatValue:" + bigFloatValue
				+ ", doubleValue:" + doubleValue
				+ ", bigDoubleValue:" + bigDoubleValue
				+ ", stringValue:" + stringValue;
	}
}
