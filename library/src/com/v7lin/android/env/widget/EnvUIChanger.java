package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class EnvUIChanger<UI> {

	public EnvUIChanger() {
		super();
	}

	public abstract void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes);

	public abstract void scheduleSkin(UI ui);

	public abstract void scheduleFont(UI ui);
}
