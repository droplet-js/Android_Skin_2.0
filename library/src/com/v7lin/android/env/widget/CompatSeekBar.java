package com.v7lin.android.env.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsSeekBar;
import android.widget.SeekBar;

import com.v7lin.android.env.EnvCallback;

/**
 * ProgressDrawable 中使用 ScaleDrawable，在小米 1 上显示不正常
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatSeekBar extends SeekBar implements XAbsSeekBarCall, EnvCallback {

	private static final boolean ALLOW_SYSRES = false;

	private EnvUIChanger<AbsSeekBar, XAbsSeekBarCall> mEnvUIChanger;

	public CompatSeekBar(Context context) {
		this(context, null);
	}

	public CompatSeekBar(Context context, AttributeSet attrs) {
//		this(context, attrs, com.android.internal.R.attr.seekBarStyle);
		this(context, attrs, InternalTransfer.transferAttr(context, "seekBarStyle"));
	}

	public CompatSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvAbsSeekBarChanger<AbsSeekBar, XAbsSeekBarCall>(context);
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, ALLOW_SYSRES, isInEditMode());
	}

	@Override
	public void setThumb(Drawable thumb) {
		super.setThumb(thumb);

		applyAttrThumb(0);
	}

	private void applyAttrThumb(int resid) {
		applyAttr(getContext(), android.R.attr.thumb, resid);
	}

	@Override
	public void setIndeterminateDrawable(Drawable d) {
		super.setIndeterminateDrawable(d);

		applyAttrIndeterminateDrawable(0);
	}

	private void applyAttrIndeterminateDrawable(int resid) {
		applyAttr(getContext(), android.R.attr.indeterminateDrawable, resid);
	}

	@Override
	public void setProgressDrawable(Drawable d) {
		super.setProgressDrawable(d);

		applyAttrProgressDrawable(0);
	}

	private void applyAttrProgressDrawable(int resid) {
		applyAttr(getContext(), android.R.attr.progressDrawable, resid);
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
			mEnvUIChanger.applyAttr(context, attr, resid, ALLOW_SYSRES, isInEditMode());
		}
	}

	@Override
	public void scheduleThumb(Drawable thumb) {
		super.setThumb(thumb);
	}

	@Override
	public void scheduleIndeterminateDrawable(Drawable d) {
		super.setIndeterminateDrawable(d);
	}

	@Override
	public void scheduleProgressDrawable(Drawable d) {
		super.setProgressDrawable(d);
	}

	@Override
	public void scheduleBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);
	}

	@Override
	public void scheduleSkin() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this, isInEditMode());
		}
	}

	@Override
	public void scheduleFont() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleFont(this, this, isInEditMode());
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this, isInEditMode());
			mEnvUIChanger.scheduleFont(this, this, isInEditMode());
		}
	}
}
