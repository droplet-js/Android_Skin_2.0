package com.v7lin.android.env.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
class EnvAbsListViewChanger<AbsLV extends AbsListView> extends EnvViewGroupChanger<AbsLV> {

	private EnvRes mListSelectorEnvRes;
	private EnvRes mCacheColorHint;

	public EnvAbsListViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.AbsListView, defStyleAttr, defStyleRes);
		mListSelectorEnvRes = array.getEnvRes(com.android.internal.R.styleable.AbsListView_listSelector, allowSysRes);
		mCacheColorHint = array.getEnvRes(com.android.internal.R.styleable.AbsListView_cacheColorHint, allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(AbsLV view) {
		super.onScheduleSkin(view);
		scheduleListSelector(view);
		scheduleCacheColorHint(view);
	}

	private void scheduleListSelector(AbsLV view) {
		Resources res = view.getResources();
		if (mListSelectorEnvRes != null) {
			Drawable drawable = res.getDrawable(mListSelectorEnvRes.getResid());
			if (drawable != null) {
				view.setSelector(drawable);
			}
		}
	}

	private void scheduleCacheColorHint(AbsLV view) {
		Resources res = view.getResources();
		if (mCacheColorHint != null) {
			int color = res.getColor(mCacheColorHint.getResid());
			if (color != 0) {
				view.setCacheColorHint(color);
			}
		}
	}
}
