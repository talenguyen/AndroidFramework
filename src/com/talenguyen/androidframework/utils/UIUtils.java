package com.talenguyen.androidframework.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

public class UIUtils {

	public static int getColorVal(long intVal) {
		StringBuilder hexString = new StringBuilder(Long.toHexString(intVal));
		final int length = hexString.length();
		switch (length) {
		case 1:
			hexString.insert(0,"#00000");
			break;
		case 2:
			hexString.insert(0,"#0000");
			break;
		case 3:
			hexString.insert(0,"#000");
			break;
		case 4:
			hexString.insert(0,"#00");
			break;
		case 5:
			hexString.insert(0,"#0");
			break;
		case 6:
			hexString.insert(0,"#");
			break;
		default:
			hexString.setLength(0);
			hexString.append("#000000");
			break;
		}
		return Color.parseColor(hexString.toString());
	}
	
	public static void showToastShort(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void showToastLongth(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
}
