package com.talenguyen.androidframework.utils;

import android.util.Log;

public class Logger {

	
	public static final boolean DEBUG = true;
	public static final String TAG = "DigitalAd";
	
	public static void logD(String message) {
		if (DEBUG) {
			Log.d(TAG, message);
		}
	}
	
	public static void logI(String message) {
		if (DEBUG) {
			Log.i(TAG, message);
		}
	}
	
	public static void logW(String message) {
		if (DEBUG) {
			Log.w(TAG, message);
		}
	}
	
	public static void logE(String message) {
		if (DEBUG) {
			Log.e(TAG, message);
		}
	}
	
	public static void logV(String message) {
		if (DEBUG) {
			Log.v(TAG, message);
		}
	}
}
