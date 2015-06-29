package com.v7lin.android.env.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatFrameLayout extends FrameLayout implements XFrameLayoutCall, EnvCallback {

	private static final boolean ALLOW_SYSRES = false;

	private EnvUIChanger<FrameLayout, XFrameLayoutCall> mEnvUIChanger;

	public CompatFrameLayout(Context context) {
		this(context, null);
	}

	public CompatFrameLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CompatFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvFrameLayoutChanger<FrameLayout, XFrameLayoutCall>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, ALLOW_SYSRES);
	}

	@Override
	public void setForeground(Drawable d) {
		super.setForeground(d);
		
		applyAttrForeground(0);
	}
	
	private void applyAttrForeground(int resid) {
		applyAttr(getContext(), android.R.attr.foreground, resid);
	}

	@Override
	public void setBackgroundColor(int color) {
		super.setBackgroundColor(color);

		applyAttrBackground(0);
	}

	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);

		applyAttrBackground(resid);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void setBackground(Drawable background) {
		super.setBackground(background);

		applyAttrBackground(0);
	}

	@Override
	public void setBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);

		applyAttrBackground(0);
	}

	private void applyAttrBackground(int resid) {
		applyAttr(getContext(), android.R.attr.background, resid);
	}

	private void applyAttr(Context context, int attr, int resid) {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.applyAttr(context, attr, resid, ALLOW_SYSRES);
		}
	}

	@Override
	public void scheduleForeground(Drawable d) {
		super.setForeground(d);
	}

	@Override
	public void scheduleBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);
	}

	@Override
	public void scheduleSkin() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this);
		}
	}

	@Override
	public void scheduleFont() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleFont(this, this);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this);
			mEnvUIChanger.scheduleFont(this, this);
		}
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		return super.drawChild(canvas, child, drawingTime);
	}
}
