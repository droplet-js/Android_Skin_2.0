package com.v7lin.android.env.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvViewGroupChanger<VG extends ViewGroup, VGC extends XViewGroupCall> extends EnvViewChanger<VG, VGC> {

	public EnvViewGroupChanger(Context context) {
		super(context);
	}

	@Override
	protected void onScheduleSkin(VG view, VGC call) {
		super.onScheduleSkin(view, call);
		scheduleViewGroupSkin(view);
	}

	@Override
	protected void onScheduleFont(VG view, VGC call) {
		super.onScheduleFont(view, call);
		scheduleViewGroupFont(view);
	}
}
