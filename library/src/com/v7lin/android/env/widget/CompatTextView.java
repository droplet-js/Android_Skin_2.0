package com.v7lin.android.env.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatTextView extends TextView implements XTextViewCall, EnvCallback {

	private static final boolean ALLOW_SYSRES = false;

	private EnvUIChanger<TextView, XTextViewCall> mEnvUIChanger;

	public CompatTextView(Context context) {
		this(context, null);
	}

	public CompatTextView(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.textViewStyle);
	}

	public CompatTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvTextViewChanger<TextView, XTextViewCall>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, ALLOW_SYSRES);
	}

	@Override
	public void setTextAppearance(Context context, int resid) {
		super.setTextAppearance(context, resid);

		applyAttrTextAppearance(context, resid);
	}

	private void applyAttrTextAppearance(Context context, int resid) {
		applyAttr(context, android.R.attr.textAppearance, resid);
	}

	@Override
	public void setHighlightColor(int color) {
		super.setHighlightColor(color);

		applyAttrHighlightColor(0);
	}

	private void applyAttrHighlightColor(int resid) {
		applyAttr(getContext(), android.R.attr.textColorHighlight, resid);
	}

	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);

		applyAttrTextColor(0);
	}

	@Override
	public void setTextColor(ColorStateList colors) {
		super.setTextColor(colors);

		applyAttrTextColor(0);
	}

	private void applyAttrTextColor(int resid) {
		applyAttr(getContext(), android.R.attr.textColor, resid);
	}

	@Override
	public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
		super.setCompoundDrawables(left, top, right, bottom);
		applyAttrCompoundDrawables(0, 0, 0, 0);
	}

	@Override
	public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
		super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		applyAttrCompoundDrawables(0, 0, 0, 0);
	}

	@Override
	public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
		super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		applyAttrCompoundDrawables(left, top, right, bottom);
	}

	private void applyAttrCompoundDrawables(int left, int top, int right, int bottom) {
		applyAttr(getContext(), android.R.attr.drawableLeft, left);
		applyAttr(getContext(), android.R.attr.drawableTop, top);
		applyAttr(getContext(), android.R.attr.drawableRight, right);
		applyAttr(getContext(), android.R.attr.drawableBottom, bottom);
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
	public void scheduleHighlightColor(int color) {
		super.setHighlightColor(color);
	}

	@Override
	public void scheduleTextColor(ColorStateList colors) {
		super.setTextColor(colors);
	}

	@Override
	public void scheduleHintTextColor(ColorStateList colors) {
		// super.setHintTextColor(colors);
		setHintTextColor(colors);
	}

	@Override
	public void scheduleLinkTextColor(ColorStateList colors) {
		// super.setLinkTextColor(colors);
		setLinkTextColor(colors);
	}

	@Override
	public void scheduleCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
		super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
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
