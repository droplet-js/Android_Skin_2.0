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
class EnvFrameLayoutChanger<FL extends FrameLayout> extends EnvViewGroupChanger<FL> {

	private static final int[] ATTRS = {
			//
			android.R.attr.foreground
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mForegroundEnvRes;

	public EnvFrameLayoutChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mForegroundEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.foreground), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(FL view) {
		super.onScheduleSkin(view);
		scheduleForeground(view);
	}
	
	private void scheduleForeground(FL view) {
		Resources res = view.getResources();
		if (mForegroundEnvRes != null) {
			Drawable drawable = res.getDrawable(mForegroundEnvRes.getResid());
			if (drawable != null) {
				view.setForeground(drawable);
			}
		}
	}
}
