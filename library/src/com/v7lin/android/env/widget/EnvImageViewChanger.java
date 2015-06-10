package com.v7lin.android.env.widget;

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
class EnvImageViewChanger<IV extends ImageView> extends EnvViewChanger<IV> {

	private EnvRes mSrcEnvRes;

	public EnvImageViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.ImageView, defStyleAttr, defStyleRes);
		mSrcEnvRes = array.getEnvRes(com.android.internal.R.styleable.ImageView_src, allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(IV view) {
		super.onScheduleSkin(view);
		scheduleSrc(view);
	}

	/**
	 * @see ImageView#setImageResource(int)
	 */
	private void scheduleSrc(IV view) {
		Resources res = view.getResources();
		if (mSrcEnvRes != null) {
			Drawable drawable = res.getDrawable(mSrcEnvRes.getResid());
			if (drawable != null) {
				view.setImageDrawable(drawable);
			}
		}
	}
}
