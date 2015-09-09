package com.v7lin.android.env.widget;

import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.AbsSeekBar;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
class EnvAbsSeekBarChanger<ASB extends AbsSeekBar, ASBC extends XAbsSeekBarCall> extends EnvProgressBarChanger<ASB, ASBC> {
	
	private static final int[] ATTRS = {
			//
			android.R.attr.thumb
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mThumbEnvRes;

	public EnvAbsSeekBarChanger(Context context) {
		super(context);
	}

	@Override
	protected void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mThumbEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.thumb), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.onApplyAttr(context, attr, resid, allowSysRes);
		
		switch (attr) {
		case android.R.attr.thumb: {
			EnvRes res = new EnvRes(resid);
			mThumbEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(ASB view, ASBC call) {
		super.onScheduleSkin(view, call);
		scheduleThumb(view, call);
	}

	private void scheduleThumb(ASB view, ASBC call) {
		Resources res = view.getResources();
		if (mThumbEnvRes != null) {
			Drawable drawable = res.getDrawable(mThumbEnvRes.getResid());
			if (drawable != null) {
				call.scheduleThumb(drawable);
			}
		}
	}
}
