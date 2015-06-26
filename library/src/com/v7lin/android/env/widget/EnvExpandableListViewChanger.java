package com.v7lin.android.env.widget;

import java.util.Arrays;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvExpandableListViewChanger<ELV extends ExpandableListView, ELVC extends XExpandableListViewCall> extends EnvListViewChanger<ELV, ELVC> {

	private static final int[] ATTRS = {
			//
			android.R.attr.groupIndicator,
			//
			android.R.attr.childIndicator,
			//
			android.R.attr.childDivider
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mGroupIndicatorEnvRes;
	private EnvRes mChildIndicatorEnvRes;
	private EnvRes mChildDividerEnvRes;

	public EnvExpandableListViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);

		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mGroupIndicatorEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.groupIndicator), allowSysRes);
		mChildIndicatorEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.childIndicator), allowSysRes);
		mChildDividerEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.childDivider), allowSysRes);
		array.recycle();
	}

	@Override
	public void applyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.applyAttr(context, attr, resid, allowSysRes);

		switch (attr) {
		case android.R.attr.groupIndicator: {
			EnvRes res = new EnvRes(resid);
			mGroupIndicatorEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		case android.R.attr.childIndicator: {
			EnvRes res = new EnvRes(resid);
			mChildIndicatorEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		case android.R.attr.childDivider: {
			EnvRes res = new EnvRes(resid);
			mChildDividerEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(ELV view, ELVC call) {
		super.onScheduleSkin(view, call);
		scheduleGroup(view, call);
		scheduleChild(view, call);
	}

	private void scheduleGroup(ELV view, ELVC call) {
		Resources res = view.getResources();
		if (mGroupIndicatorEnvRes != null) {
			Drawable drawable = res.getDrawable(mGroupIndicatorEnvRes.getResid());
			if (drawable != null) {
				call.scheduleGroupIndicator(drawable);
			}
		}
	}

	private void scheduleChild(ELV view, ELVC call) {
		Resources res = view.getResources();
		if (mChildIndicatorEnvRes != null) {
			Drawable drawable = res.getDrawable(mChildIndicatorEnvRes.getResid());
			if (drawable != null) {
				call.scheduleChildIndicator(drawable);
			}
		}
		if (mChildDividerEnvRes != null) {
			Drawable drawable = res.getDrawable(mChildDividerEnvRes.getResid());
			if (drawable != null) {
				call.scheduleChildDivider(drawable);
			}
		}
	}
}
