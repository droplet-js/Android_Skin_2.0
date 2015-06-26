package com.v7lin.android.env.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatSpinner extends Spinner implements XViewGroupCall, EnvCallback {

	private static final boolean ALLOW_SYSRES = false;

	private EnvUIChanger<ViewGroup, XViewGroupCall> mEnvUIChanger;

	public CompatSpinner(Context context) {
		this(context, null);
	}

	public CompatSpinner(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.spinnerStyle);
	}

	public CompatSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvViewGroupChanger<ViewGroup, XViewGroupCall>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, ALLOW_SYSRES);
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
}
