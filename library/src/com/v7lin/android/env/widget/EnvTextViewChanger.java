package com.v7lin.android.env.widget;

import java.util.Arrays;

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
	
	private static final int[] ATTRS = {
			//
			android.R.attr.drawableLeft,
			//
			android.R.attr.drawableTop,
			//
			android.R.attr.drawableRight,
			//
			android.R.attr.drawableBottom,
			//
			android.R.attr.drawablePadding,
			//
			android.R.attr.textAppearance,
			//
			android.R.attr.textColorHighlight,
			//
			android.R.attr.textColor,
			//
			android.R.attr.textColorHint,
			//
			android.R.attr.textColorLink
	};

	private static final int[] ATTRS_TEXT = {
			//
			android.R.attr.textColorHighlight,
			//
			android.R.attr.textColor,
			//
			android.R.attr.textColorHint,
			//
			android.R.attr.textColorLink
	};

	static {
		Arrays.sort(ATTRS);
		Arrays.sort(ATTRS_TEXT);
	}

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
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mDrawableLeftEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.drawableLeft), allowSysRes);
		mDrawableTopEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.drawableTop), allowSysRes);
		mDrawableRightEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.drawableRight), allowSysRes);
		mDrawableBottomEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.drawableBottom), allowSysRes);
		mDrawablePaddingEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.drawablePadding), allowSysRes);

		EnvRes textAppearanceEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.textAppearance), allowSysRes);
		if (textAppearanceEnvRes != null) {
			EnvTypedArray textAppearanceArray = EnvTypedArray.obtainStyledAttributes(context, textAppearanceEnvRes.getResid(), ATTRS_TEXT);

			mTextColorHighlightEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColorHighlight), allowSysRes);
			mTextColorEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColor), allowSysRes);
			mTextColorHintEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColorHint), allowSysRes);
			mTextColorLinkEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColorLink), allowSysRes);

			textAppearanceArray.recycle();
		}

		mTextColorHighlightEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.textColorHighlight), mTextColorHighlightEnvRes, allowSysRes);
		mTextColorEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.textColor), mTextColorEnvRes, allowSysRes);
		mTextColorHintEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.textColorHint), mTextColorHintEnvRes, allowSysRes);
		mTextColorLinkEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.textColorLink), mTextColorLinkEnvRes, allowSysRes);
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
