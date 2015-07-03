package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class EnvUIChanger<UI, UIC> {

	public EnvUIChanger() {
		super();
	}

	public final void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes, boolean isInEditMode) {
		if (!isInEditMode) {
			onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		}
	}
	
	protected abstract void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes);

	public final void applyAttr(Context context, int attr, int resid, boolean allowSysRes, boolean isInEditMode) {
		if (!isInEditMode) {
			onApplyAttr(context, attr, resid, allowSysRes);
		}
	}
	
	protected abstract void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes);

	public final void scheduleSkin(UI ui, UIC call, boolean isInEditMode) {
		if (!isInEditMode) {
			onScheduleSkin(ui, call);
		}
	}

	protected abstract void onScheduleSkin(UI ui, UIC call);

	public final void scheduleFont(UI ui, UIC call, boolean isInEditMode) {
		if (!isInEditMode) {
			onScheduleFont(ui, call);
		}
	}

	protected abstract void onScheduleFont(UI ui, UIC call);
}
