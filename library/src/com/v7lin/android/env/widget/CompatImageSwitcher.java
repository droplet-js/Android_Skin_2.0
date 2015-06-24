package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageSwitcher;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatImageSwitcher extends ImageSwitcher implements EnvCallback {

	private EnvUIChanger<ImageSwitcher> mEnvUIChanger;

	public CompatImageSwitcher(Context context) {
		this(context, null);
	}

	public CompatImageSwitcher(Context context, AttributeSet attrs) {
		super(context, attrs);

		mEnvUIChanger = new EnvFrameLayoutChanger<ImageSwitcher>();
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
