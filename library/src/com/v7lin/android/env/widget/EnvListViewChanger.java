package com.v7lin.android.env.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ListView;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvListViewChanger<LV extends ListView> extends EnvAbsListViewChanger<LV> {

	private EnvRes mDividerEnvRes;
	private EnvRes mDividerHeightEnvRes;

	public EnvListViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.ListView, defStyleAttr, defStyleRes);
		mDividerEnvRes = array.getEnvRes(com.android.internal.R.styleable.ListView_divider, allowSysRes);
		mDividerHeightEnvRes = array.getEnvRes(com.android.internal.R.styleable.ListView_dividerHeight, allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(LV view) {
		super.onScheduleSkin(view);
		scheduleDivider(view);
	}

	private void scheduleDivider(LV view) {
		Resources res = view.getResources();
		if (mDividerEnvRes != null) {
			Drawable drawable = res.getDrawable(mDividerEnvRes.getResid());
			if (drawable != null) {
				view.setDivider(drawable);
			}
		}
		if (mDividerHeightEnvRes != null) {
			final int dividerHeight = res.getDimensionPixelSize(mDividerHeightEnvRes.getResid());
			view.setDividerHeight(dividerHeight);
		}
	}
}
