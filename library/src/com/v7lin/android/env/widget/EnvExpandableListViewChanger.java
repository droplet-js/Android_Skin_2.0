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
public class EnvExpandableListViewChanger<ELV extends ExpandableListView> extends EnvListViewChanger<ELV> {

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
	protected void onScheduleSkin(ELV view) {
		super.onScheduleSkin(view);
		scheduleGroup(view);
		scheduleChild(view);
	}

	private void scheduleGroup(ELV view) {
		Resources res = view.getResources();
		if (mGroupIndicatorEnvRes != null) {
			Drawable drawable = res.getDrawable(mGroupIndicatorEnvRes.getResid());
			if (drawable != null) {
				view.setGroupIndicator(drawable);
			}
		}
	}

	private void scheduleChild(ELV view) {
		Resources res = view.getResources();
		if (mChildIndicatorEnvRes != null) {
			Drawable drawable = res.getDrawable(mChildIndicatorEnvRes.getResid());
			if (drawable != null) {
				view.setChildIndicator(drawable);
			}
		}
		if (mChildDividerEnvRes != null) {
			Drawable drawable = res.getDrawable(mChildDividerEnvRes.getResid());
			if (drawable != null) {
				view.setChildDivider(drawable);
			}
		}
	}
}
