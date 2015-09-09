package com.v7lin.android.env.widget;

import java.util.Arrays;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvActivityChanger extends EnvUIChanger<Activity, XActivityCall> {

	private static final int[] ATTRS = {
			//
			android.R.attr.windowBackground
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mWindowBackgroundEnvRes;

	public EnvActivityChanger(Context context) {
		super(context);
	}

	@Override
	protected void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, ATTRS);
		mWindowBackgroundEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.windowBackground), allowSysRes);
		array.recycle();
	}

	@Override
	public void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		
	}

	@Override
	protected void onScheduleSkin(Activity activity, XActivityCall call) {
		scheduleWindowBackground(activity);
		scheduleViewSkin(activity.findViewById(android.R.id.content));
	}

	private void scheduleWindowBackground(Activity activity) {
		if (mWindowBackgroundEnvRes != null) {
			String typeName = activity.getResources().getResourceTypeName(mWindowBackgroundEnvRes.getResid());
			if (TextUtils.equals(typeName, "color")) {
				int backgroundColor = activity.getResources().getColor(mWindowBackgroundEnvRes.getResid());
				activity.getWindow().setBackgroundDrawable(new ColorDrawable(backgroundColor));
			} else {
				activity.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(mWindowBackgroundEnvRes.getResid()));
			}
		}
	}

	@Override
	protected void onScheduleFont(Activity activity, XActivityCall call) {
		scheduleViewFont(activity.findViewById(android.R.id.content));
	}
}
