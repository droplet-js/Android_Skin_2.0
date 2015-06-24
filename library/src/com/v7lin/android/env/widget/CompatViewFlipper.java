package com.v7lin.android.env.widget;

import com.v7lin.android.env.EnvCallback;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatViewFlipper extends ViewFlipper implements EnvCallback {

	private EnvUIChanger<ViewFlipper> mEnvUIChanger;

	public CompatViewFlipper(Context context) {
		this(context, null);
	}

	public CompatViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);

		mEnvUIChanger = new EnvFrameLayoutChanger<ViewFlipper>();
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
