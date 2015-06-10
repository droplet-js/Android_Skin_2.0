package com.v7lin.android.env.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatStatusBar extends LinearLayout implements EnvCallback {

	private EnvUIChanger<LinearLayout> mEnvUIChanger;

	public CompatStatusBar(Context context) {
		this(context, null);
	}

	public CompatStatusBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mEnvUIChanger = new EnvViewGroupChanger<LinearLayout>();
		mEnvUIChanger.applyStyle(context, attrs, 0, 0, true);
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
