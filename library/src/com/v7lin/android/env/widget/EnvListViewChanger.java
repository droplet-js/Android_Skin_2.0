package com.v7lin.android.env.widget;

import java.util.Arrays;

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
public class EnvListViewChanger<LV extends ListView, LVC extends XListViewCall> extends EnvAbsListViewChanger<LV, LVC> {

	private static final int[] ATTRS = {
		//
		android.R.attr.divider
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mDividerEnvRes;

	public EnvListViewChanger() {
		super();
	}

	@Override
	protected void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mDividerEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.divider), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.onApplyAttr(context, attr, resid, allowSysRes);
		
		switch (attr) {
		case android.R.attr.divider: {
			EnvRes res = new EnvRes(resid);
			mDividerEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(LV view, LVC call) {
		super.onScheduleSkin(view, call);
		scheduleDivider(view, call);
	}

	private void scheduleDivider(LV view, LVC call) {
		Resources res = view.getResources();
		if (mDividerEnvRes != null) {
			Drawable drawable = res.getDrawable(mDividerEnvRes.getResid());
			if (drawable != null) {
				call.scheduleDivider(drawable);
			}
		}
	}
}
