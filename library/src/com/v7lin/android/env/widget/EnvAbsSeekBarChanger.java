package com.v7lin.android.env.widget;

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
class EnvAbsSeekBarChanger<AbsSB extends AbsSeekBar> extends EnvProgressBarChanger<AbsSB> {

	private EnvRes mThumbEnvRes;
	private EnvRes mThumbOffsetEnvRes;

	public EnvAbsSeekBarChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.SeekBar, defStyleAttr, defStyleRes);
		mThumbEnvRes = array.getEnvRes(com.android.internal.R.styleable.SeekBar_thumb, allowSysRes);
		mThumbOffsetEnvRes = array.getEnvRes(com.android.internal.R.styleable.SeekBar_thumbOffset, allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(AbsSB view) {
		super.onScheduleSkin(view);
		scheduleThumb(view);
	}

	private void scheduleThumb(AbsSB view) {
		Resources res = view.getResources();
		if (mThumbEnvRes != null) {
			Drawable drawable = res.getDrawable(mThumbEnvRes.getResid());
			if (drawable != null) {
				view.setThumb(drawable);
			}
		}
		if (mThumbOffsetEnvRes != null) {
			int thumbOffset = res.getDimensionPixelOffset(mThumbOffsetEnvRes.getResid());
			if (view.getThumbOffset() != thumbOffset) {
				view.setThumbOffset(thumbOffset);
			}
		}
	}
}
