package com.v7lin.android.env.widget;

import java.util.Arrays;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvAutoCompleteTextViewChanger<ACTV extends AutoCompleteTextView> extends EnvTextViewChanger<ACTV> {

	private static final int[] ATTRS = {
			//
			android.R.attr.dropDownSelector
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mDropDownSelectorEnvRes;

	public EnvAutoCompleteTextViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mDropDownSelectorEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.dropDownSelector), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(ACTV view) {
		super.onScheduleSkin(view);
		if (mDropDownSelectorEnvRes != null) {

		}
	}
}
