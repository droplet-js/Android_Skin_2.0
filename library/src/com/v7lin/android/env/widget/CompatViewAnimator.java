package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatViewAnimator extends ViewAnimator implements EnvCallback {

	private EnvUIChanger<ViewAnimator> mEnvUIChanger;

	public CompatViewAnimator(Context context) {
		this(context, null);
	}

	public CompatViewAnimator(Context context, AttributeSet attrs) {
		super(context, attrs);

		mEnvUIChanger = new EnvFrameLayoutChanger<ViewAnimator>();
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
