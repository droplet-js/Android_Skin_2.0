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
public class EnvCheckedTextViewChanger<CTV extends CheckedTextView, CTVC extends XCheckedTextViewCall> extends EnvTextViewChanger<CTV, CTVC> {

	private static final int[] ATTRS = {
			//
			android.R.attr.checkMark
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mCheckMarkEnvRes;

	@Override
	protected void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mCheckMarkEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.checkMark), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.onApplyAttr(context, attr, resid, allowSysRes);
		
		switch (attr) {
		case android.R.attr.checkMark: {
			EnvRes res = new EnvRes(resid);
			mCheckMarkEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(CTV view, CTVC call) {
		super.onScheduleSkin(view, call);
		scheduleCheckMarkDrawable(view, call);
	}

	private void scheduleCheckMarkDrawable(CTV view, CTVC call) {
		Resources res = view.getResources();
		if (mCheckMarkEnvRes != null) {
			Drawable drawable = res.getDrawable(mCheckMarkEnvRes.getResid());
			if (drawable != null) {
				call.scheduleCheckMarkDrawable(drawable);
			}
		}
	}
}
