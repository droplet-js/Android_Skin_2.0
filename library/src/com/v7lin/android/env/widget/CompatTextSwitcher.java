package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextSwitcher;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatTextSwitcher extends TextSwitcher implements EnvCallback {
	
	private EnvUIChanger<TextSwitcher> mEnvUIChanger;

	public CompatTextSwitcher(Context context) {
		this(context, null);
	}

	public CompatTextSwitcher(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mEnvUIChanger = new EnvFrameLayoutChanger<TextSwitcher>();
		mEnvUIChanger.applyStyle(context, attrs, 0, 0, false);
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
