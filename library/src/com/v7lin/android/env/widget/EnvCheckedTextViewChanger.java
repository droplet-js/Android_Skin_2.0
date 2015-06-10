package com.v7lin.android.env.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

import com.android.internal.R;
import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvCheckedTextViewChanger<CTV extends CheckedTextView> extends EnvTextViewChanger<CTV> {

	private EnvRes mCheckMarkEnvRes;

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.CheckedTextView, defStyleAttr, defStyleRes);
		mCheckMarkEnvRes = array.getEnvRes(R.styleable.CheckedTextView_checkMark, allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(CTV view) {
		super.onScheduleSkin(view);
		scheduleCheckMarkDrawable(view);
	}

	private void scheduleCheckMarkDrawable(CTV view) {
		Resources res = view.getResources();
		if (mCheckMarkEnvRes != null) {
			Drawable drawable = res.getDrawable(mCheckMarkEnvRes.getResid());
			if (drawable != null) {
				view.setCheckMarkDrawable(drawable);
			}
		}
	}
}
