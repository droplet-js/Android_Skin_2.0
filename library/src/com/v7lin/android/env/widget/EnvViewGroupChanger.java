package com.v7lin.android.env.widget;

import android.view.View;
import android.view.ViewGroup;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvViewGroupChanger<VG extends ViewGroup> extends EnvViewChanger<VG> {

	public EnvViewGroupChanger() {
		super();
	}

	@Override
	protected void onScheduleSkin(VG view) {
		super.onScheduleSkin(view);
		scheduleViewGroupSkin(view);
	}

	private void scheduleViewSkin(View view) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleSkin();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupSkin((ViewGroup) view);
			}
		}
	}

	private void scheduleViewGroupSkin(ViewGroup group) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewSkin(child);
			}
		}
	}

	@Override
	protected void onScheduleFont(VG view) {
		super.onScheduleFont(view);
		scheduleViewGroupFont(view);
	}

	private void scheduleViewFont(View view) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleFont();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupFont((ViewGroup) view);
			}
		}
	}

	private void scheduleViewGroupFont(ViewGroup group) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewFont(child);
			}
		}
	}
}
