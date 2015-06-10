package com.v7lin.android.env.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvResourcesHelper;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
class EnvTextViewChanger<TV extends TextView> extends EnvViewChanger<TV> {

	private EnvRes mDrawableLeftEnvRes;
	private EnvRes mDrawableTopEnvRes;
	private EnvRes mDrawableRightEnvRes;
	private EnvRes mDrawableBottomEnvRes;
	private EnvRes mDrawablePaddingEnvRes;

	private EnvRes mTextColorHighlightEnvRes;
	private EnvRes mTextColorEnvRes;
	private EnvRes mTextColorHintEnvRes;
	private EnvRes mTextColorLinkEnvRes;

	public EnvTextViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.TextView, defStyleAttr, defStyleRes);
		mDrawableLeftEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_drawableLeft, allowSysRes);
		mDrawableTopEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_drawableTop, allowSysRes);
		mDrawableRightEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_drawableRight, allowSysRes);
		mDrawableBottomEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_drawableBottom, allowSysRes);
		mDrawablePaddingEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_drawablePadding, allowSysRes);

		EnvRes textAppearanceEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_textAppearance, allowSysRes);
		if (textAppearanceEnvRes != null) {
			EnvTypedArray textAppearanceArray = EnvTypedArray.obtainStyledAttributes(context, textAppearanceEnvRes.getResid(), com.android.internal.R.styleable.TextAppearance);

			mTextColorHighlightEnvRes = textAppearanceArray.getEnvRes(com.android.internal.R.styleable.TextAppearance_textColorHighlight, allowSysRes);
			mTextColorEnvRes = textAppearanceArray.getEnvRes(com.android.internal.R.styleable.TextAppearance_textColor, allowSysRes);
			mTextColorHintEnvRes = textAppearanceArray.getEnvRes(com.android.internal.R.styleable.TextAppearance_textColorHint, allowSysRes);
			mTextColorLinkEnvRes = textAppearanceArray.getEnvRes(com.android.internal.R.styleable.TextAppearance_textColorLink, allowSysRes);

			textAppearanceArray.recycle();
		}

		mTextColorHighlightEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_textColorHighlight, mTextColorHighlightEnvRes, allowSysRes);
		mTextColorEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_textColor, mTextColorEnvRes, allowSysRes);
		mTextColorHintEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_textColorHint, mTextColorHintEnvRes, allowSysRes);
		mTextColorLinkEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_textColorLink, mTextColorLinkEnvRes, allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(TV view) {
		super.onScheduleSkin(view);
		scheduleCompoundDrawable(view);
		scheduleTextColor(view);
	}

	@Override
	protected void onScheduleFont(TV view) {
		super.onScheduleFont(view);
		Typeface tf = EnvResourcesHelper.getFontFamily(view.getContext()).getTypeface();
		view.setTypeface(tf);
	}

	private void scheduleCompoundDrawable(TV view) {
		Resources res = view.getResources();
		if (mDrawableLeftEnvRes != null || mDrawableTopEnvRes != null || mDrawableRightEnvRes != null || mDrawableBottomEnvRes != null) {
			Drawable drawableLeft = mDrawableLeftEnvRes != null ? res.getDrawable(mDrawableLeftEnvRes.getResid()) : null;
			Drawable drawableTop = mDrawableTopEnvRes != null ? res.getDrawable(mDrawableTopEnvRes.getResid()) : null;
			Drawable drawableRight = mDrawableRightEnvRes != null ? res.getDrawable(mDrawableRightEnvRes.getResid()) : null;
			Drawable drawableBottom = mDrawableBottomEnvRes != null ? res.getDrawable(mDrawableBottomEnvRes.getResid()) : null;
			if (drawableLeft != null || drawableTop != null || drawableRight != null || drawableBottom != null) {
				final int drawablePadding = view.getCompoundDrawablePadding();
				view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
				view.setCompoundDrawablePadding(drawablePadding);
			}
		}
		if (mDrawablePaddingEnvRes != null) {
			final int drawablePadding = res.getDimensionPixelSize(mDrawablePaddingEnvRes.getResid());
			view.setCompoundDrawablePadding(drawablePadding);
		}
	}

	private void scheduleTextColor(TV view) {
		Resources res = view.getResources();
		if (mTextColorHighlightEnvRes != null) {
			int textColorHighlight = res.getColor(mTextColorHighlightEnvRes.getResid());
			view.setHighlightColor(textColorHighlight);
		}
		if (mTextColorEnvRes != null) {
			ColorStateList textColor = res.getColorStateList(mTextColorEnvRes.getResid());
			view.setTextColor(textColor);
		}
		if (mTextColorHintEnvRes != null) {
			ColorStateList textColorHint = res.getColorStateList(mTextColorHintEnvRes.getResid());
			view.setHintTextColor(textColorHint);
		}
		if (mTextColorLinkEnvRes != null) {
			ColorStateList textColorLink = res.getColorStateList(mTextColorLinkEnvRes.getResid());
			view.setLinkTextColor(textColorLink);
		}
	}
}
