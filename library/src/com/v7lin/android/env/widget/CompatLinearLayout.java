package com.v7lin.android.env.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatLinearLayout extends LinearLayout implements XViewGroupCall, EnvCallback {

	public static final int SHOW_DIVIDER_NONE_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_NONE_SUPPORT;
	public static final int SHOW_DIVIDER_BEGINNING_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_BEGINNING_SUPPORT;
	public static final int SHOW_DIVIDER_MIDDLE_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_MIDDLE_SUPPORT;
	public static final int SHOW_DIVIDER_END_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_END_SUPPORT;

	private static final boolean ALLOW_SYSRES = false;

	private LinearLayoutCompat mLinearLayoutCompat;
	private EnvUIChanger<ViewGroup, XViewGroupCall> mEnvUIChanger;

	public CompatLinearLayout(Context context) {
		this(context, null);
	}

	public CompatLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		mLinearLayoutCompat = LinearLayoutCompat.get(this);

		mEnvUIChanger = new EnvViewGroupChanger<ViewGroup, XViewGroupCall>();
		mEnvUIChanger.applyStyle(context, attrs, 0, 0, ALLOW_SYSRES);
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

	public void setDividerDrawableSupport(Drawable divider) {
		mLinearLayoutCompat.setDividerDrawableSupport(divider);
	}

	public void setShowDividersSupport(int showDividers) {
		mLinearLayoutCompat.setShowDividersSupport(showDividers);
	}

	public void setDividerPaddingSupport(int padding) {
		mLinearLayoutCompat.setDividerPaddingSupport(padding);
	}

	@Override
	protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
		mLinearLayoutCompat.measureChildWithMarginsSupport(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
		super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mLinearLayoutCompat.onDrawSupport(canvas);
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		return super.drawChild(canvas, child, drawingTime);
	}
}
