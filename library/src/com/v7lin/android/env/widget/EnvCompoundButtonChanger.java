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
class EnvCompoundButtonChanger<CB extends CompoundButton, CBC extends XCompoundButtonCall> extends EnvTextViewChanger<CB, CBC> {

	private static final int[] ATTRS = {
	//
	android.R.attr.button };

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mButtonEnvRes;

	public EnvCompoundButtonChanger() {
		super();
	}

	@Override
	protected void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mButtonEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.button), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.onApplyAttr(context, attr, resid, allowSysRes);

		switch (attr) {
		case android.R.attr.button: {
			EnvRes res = new EnvRes(resid);
			mButtonEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(CB view, CBC call) {
		super.onScheduleSkin(view, call);
		scheduleButtonDrawable(view, call);
	}

	private void scheduleButtonDrawable(CB view, CBC call) {
		Resources res = view.getResources();
		if (mButtonEnvRes != null) {
			Drawable drawable = res.getDrawable(mButtonEnvRes.getResid());
			if (drawable != null) {
				call.scheduleButtonDrawable(drawable);
			}
		}
	}
}
