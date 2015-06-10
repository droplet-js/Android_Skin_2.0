package com.v7lin.android.env.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatFrameLayout extends FrameLayout implements EnvCallback {

	private EnvUIChanger<FrameLayout> mEnvUIChanger;

	public CompatFrameLayout(Context context) {
		this(context, null);
	}

	public CompatFrameLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CompatFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvFrameLayoutChanger<FrameLayout>();
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

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		return super.drawChild(canvas, child, drawingTime);
	}
}
