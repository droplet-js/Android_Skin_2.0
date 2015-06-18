package com.v7lin.android.env.widget;

import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
class EnvCompoundButtonChanger<CB extends CompoundButton> extends EnvTextViewChanger<CB> {

	private static final int[] ATTRS = {
			//
			android.R.attr.button
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mButtonEnvRes;

	public EnvCompoundButtonChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mButtonEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.button), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(CB view) {
		super.onScheduleSkin(view);
		scheduleButton(view);
	}

	private void scheduleButton(CB view) {
		Resources res = view.getResources();
		if (mButtonEnvRes != null) {
			Drawable drawable = res.getDrawable(mButtonEnvRes.getResid());
			if (drawable != null) {
				view.setButtonDrawable(drawable);
			}
		}
	}
}
