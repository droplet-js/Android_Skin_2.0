package com.v7lin.android.env.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvActivityChanger extends EnvUIChanger<Activity> {

	private EnvRes mWindowBackgroundEnvRes;

	public EnvActivityChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, new int[] { android.R.attr.windowBackground });
		mWindowBackgroundEnvRes = array.getEnvRes(0, allowSysRes);
		array.recycle();
	}

	@Override
	public void scheduleSkin(Activity activity) {
		onScheduleSkin(activity);
	}

	protected void onScheduleSkin(Activity activity) {
		scheduleWindowBackground(activity);
	}

	private void scheduleWindowBackground(Activity activity) {
		if (mWindowBackgroundEnvRes != null && mWindowBackgroundEnvRes.isValid()) {
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
	public void scheduleFont(Activity activity) {

	}

}
