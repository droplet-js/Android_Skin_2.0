package com.v7lin.android.env.widget;

import java.util.Arrays;

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
class EnvAbsListViewChanger<ALV extends AbsListView, ALVC extends XAbsListViewCall> extends EnvViewGroupChanger<ALV, ALVC> {

	private static final int[] ATTRS = {
			//
			android.R.attr.listSelector,
			//
			android.R.attr.cacheColorHint
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mListSelectorEnvRes;
	private EnvRes mCacheColorHint;

	public EnvAbsListViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mListSelectorEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.listSelector), allowSysRes);
		mCacheColorHint = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.cacheColorHint), allowSysRes);
		array.recycle();
	}

	@Override
	public void applyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.applyAttr(context, attr, resid, allowSysRes);

		switch (attr) {
		case android.R.attr.listSelector: {
			EnvRes res = new EnvRes(resid);
			mListSelectorEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		case android.R.attr.cacheColorHint: {
			EnvRes res = new EnvRes(resid);
			mCacheColorHint = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(ALV view, ALVC call) {
		super.onScheduleSkin(view, call);
		scheduleSelector(view, call);
		scheduleCacheColorHint(view, call);
	}

	private void scheduleSelector(ALV view, ALVC call) {
		Resources res = view.getResources();
		if (mListSelectorEnvRes != null) {
			Drawable drawable = res.getDrawable(mListSelectorEnvRes.getResid());
			if (drawable != null) {
				call.scheduleSelector(drawable);
			}
		}
	}

	private void scheduleCacheColorHint(ALV view, ALVC call) {
		Resources res = view.getResources();
		if (mCacheColorHint != null) {
			int color = res.getColor(mCacheColorHint.getResid());
			if (color != 0) {
				call.scheduleCacheColorHint(color);
			}
		}
	}
}
