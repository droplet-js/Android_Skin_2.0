package com.v7lin.android.env.widget;

import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
class EnvImageViewChanger<IV extends ImageView, IVC extends XImageViewCall> extends EnvViewChanger<IV, IVC> {
	
	private static final int[] ATTRS = {
			//
			android.R.attr.src
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mSrcEnvRes;

	public EnvImageViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mSrcEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.src), allowSysRes);
		array.recycle();
	}

	@Override
	public void applyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.applyAttr(context, attr, resid, allowSysRes);
		
		switch (attr) {
		case android.R.attr.src: {
			EnvRes res = new EnvRes(resid);
			mSrcEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(IV view, IVC call) {
		super.onScheduleSkin(view, call);
		scheduleSrc(view, call);
	}

	/**
	 * @see ImageView#setImageResource(int)
	 */
	private void scheduleSrc(IV view, IVC call) {
		Resources res = view.getResources();
		if (mSrcEnvRes != null) {
			Drawable drawable = res.getDrawable(mSrcEnvRes.getResid());
			if (drawable != null) {
				call.scheduleImageDrawable(drawable);
			}
		}
	}
}
