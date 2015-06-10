package com.v7lin.android.env.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatLinearLayout extends LinearLayout implements EnvCallback {

	public static final int SHOW_DIVIDER_NONE_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_NONE_SUPPORT;
	public static final int SHOW_DIVIDER_BEGINNING_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_BEGINNING_SUPPORT;
	public static final int SHOW_DIVIDER_MIDDLE_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_MIDDLE_SUPPORT;
	public static final int SHOW_DIVIDER_END_SUPPORT = LinearLayoutCompat.SHOW_DIVIDER_END_SUPPORT;

	private LinearLayoutCompat mLinearLayoutCompat;
	private EnvUIChanger<LinearLayout> mEnvUIChanger;

	public CompatLinearLayout(Context context) {
		this(context, null);
	}

	public CompatLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		mLinearLayoutCompat = LinearLayoutCompat.get(this);

		mEnvUIChanger = new EnvViewGroupChanger<LinearLayout>();
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
