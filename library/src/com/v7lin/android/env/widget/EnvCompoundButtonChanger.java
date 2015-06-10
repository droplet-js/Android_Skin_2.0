package com.v7lin.android.env.widget;

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

	private EnvRes mButtonEnvRes;

	public EnvCompoundButtonChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.CompoundButton, defStyleAttr, defStyleRes);
		mButtonEnvRes = array.getEnvRes(com.android.internal.R.styleable.CompoundButton_button, allowSysRes);
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
