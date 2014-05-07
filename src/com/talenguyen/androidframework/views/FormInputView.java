package com.talenguyen.androidframework.views;

import com.talenguyen.androidframework.utils.NumberTextWatcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

public class FormInputView extends EditText {
	
	private static final String PREFIX = "http://schemas.android.com/apk/lib/"; 
	private static final String NAME = "name";
	private static final String ERROR = "error";
	private static final String FORMAT = "format";
	private static final String NUMBER = "number";
	private static final CharSequence DEFAUL_ERROR_TEXT = "Field is required.";
	private String key;
	private String errorText;

	public FormInputView(Context context) {
		super(context);
	}
	
	public FormInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public FormInputView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		if (attrs != null) {
			final String nameSpace = PREFIX + context.getPackageName();
			key = attrs.getAttributeValue(nameSpace, NAME);
			errorText = attrs.getAttributeValue(nameSpace, ERROR);
			final String format = attrs.getAttributeValue(nameSpace, FORMAT);
			if (NUMBER.equals(format)) {
				addTextChangedListener(new NumberTextWatcher(this));
			}
			load();
		}
	}
	
	public void load() {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		final Context context = getContext();
		final SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		final String value = sharedPreferences.getString(key, "");
		setText(value);
	}

	public String getValue() {
		return getText().toString().replace(",", "");
	}
	
	public boolean verify() {
		final String value = getText().toString();
		if (TextUtils.isEmpty(value)) {
			if (errorText == null) {
				setError(DEFAUL_ERROR_TEXT);
			} else {
				setError(errorText);
			}
			return false;
		}
		save();
		return true;
	}

	public void save() {
		final String value = getText().toString();
		if (key == null) {
			return;
		}
		final Context context = getContext();
		final SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
		final Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
}
