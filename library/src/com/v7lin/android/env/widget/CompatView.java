package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatView extends View implements EnvCallback {

	private EnvUIChanger<View> mEnvUIChanger;

	public CompatView(Context context) {
		this(context, null);
	}

	public CompatView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CompatView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvViewChanger<View>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, false);
	}

	@Override
	public void scheduleSkin() {
		mEnvUIChanger.scheduleSkin(this);
	}

	@Override
	public void scheduleFont() {
		mEnvUIChanger.scheduleFont(this);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mEnvUIChanger.scheduleSkin(this);
		mEnvUIChanger.scheduleFont(this);
	}
}
