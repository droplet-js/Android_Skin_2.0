package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatToggleButton extends ToggleButton implements EnvCallback {

	private EnvUIChanger<ToggleButton> mEnvUIChanger;

	public CompatToggleButton(Context context) {
		this(context, null);
	}

	public CompatToggleButton(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.buttonStyleToggle);
	}

	public CompatToggleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvCompoundButtonChanger<ToggleButton>();
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
