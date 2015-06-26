package com.v7lin.android.env.widget;

import android.view.View;
import android.view.ViewGroup;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvViewGroupChanger<VG extends ViewGroup, VGC extends XViewGroupCall> extends EnvViewChanger<VG, VGC> {

	public EnvViewGroupChanger() {
		super();
	}

	@Override
	protected void onScheduleSkin(VG view, VGC call) {
		super.onScheduleSkin(view, call);
		scheduleViewGroupSkin(view, call);
	}

	private void scheduleViewSkin(View view, VGC call) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleSkin();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupSkin((ViewGroup) view, call);
			}
		}
	}

	private void scheduleViewGroupSkin(ViewGroup group, VGC call) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewSkin(child, call);
			}
		}
	}

	@Override
	protected void onScheduleFont(VG view, VGC call) {
		super.onScheduleFont(view, call);
		scheduleViewGroupFont(view, call);
	}

	private void scheduleViewFont(View view, VGC call) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleFont();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupFont((ViewGroup) view, call);
			}
		}
	}

	private void scheduleViewGroupFont(ViewGroup group, VGC call) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewFont(child, call);
			}
		}
	}
}
