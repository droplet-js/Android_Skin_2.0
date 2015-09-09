package com.v7lin.android.env.widget;

import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
class EnvFrameLayoutChanger<FL extends FrameLayout, FLC extends XFrameLayoutCall> extends EnvViewGroupChanger<FL, FLC> {

	private static final int[] ATTRS = {
			//
			android.R.attr.foreground
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mForegroundEnvRes;

	public EnvFrameLayoutChanger(Context context) {
		super(context);
	}

	@Override
	protected void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mForegroundEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.foreground), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.onApplyAttr(context, attr, resid, allowSysRes);

		switch (attr) {
		case android.R.attr.foreground: {
			EnvRes res = new EnvRes(resid);
			mForegroundEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(FL view, FLC call) {
		super.onScheduleSkin(view, call);
		scheduleForeground(view, call);
	}
	
	private void scheduleForeground(FL view, FLC call) {
		Resources res = view.getResources();
		if (mForegroundEnvRes != null) {
			Drawable drawable = res.getDrawable(mForegroundEnvRes.getResid());
			if (drawable != null) {
				call.scheduleForeground(drawable);
			}
		}
	}
}
