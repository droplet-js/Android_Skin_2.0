package com.v7lin.android.env.widget;

import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvCheckedTextViewChanger<CTV extends CheckedTextView> extends EnvTextViewChanger<CTV> {

	private static final int[] ATTRS = {
			//
			android.R.attr.checkMark
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mCheckMarkEnvRes;

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mCheckMarkEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.checkMark), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(CTV view) {
		super.onScheduleSkin(view);
		scheduleCheckMarkDrawable(view);
	}

	private void scheduleCheckMarkDrawable(CTV view) {
		Resources res = view.getResources();
		if (mCheckMarkEnvRes != null) {
			Drawable drawable = res.getDrawable(mCheckMarkEnvRes.getResid());
			if (drawable != null) {
				view.setCheckMarkDrawable(drawable);
			}
		}
	}
}
