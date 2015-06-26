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
class EnvTextViewChanger<TV extends TextView, TVC extends XTextViewCall> extends EnvViewChanger<TV, TVC> {

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
	public void applyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.applyAttr(context, attr, resid, allowSysRes);

		switch (attr) {
		case android.R.attr.textAppearance: {
			EnvTypedArray textAppearanceArray = EnvTypedArray.obtainStyledAttributes(context, resid, ATTRS_TEXT);

			mTextColorHighlightEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColorHighlight), mTextColorHighlightEnvRes, allowSysRes);
			mTextColorEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColor), mTextColorEnvRes, allowSysRes);
			mTextColorHintEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColorHint), mTextColorHintEnvRes, allowSysRes);
			mTextColorLinkEnvRes = textAppearanceArray.getEnvRes(Arrays.binarySearch(ATTRS_TEXT, android.R.attr.textColorLink), mTextColorLinkEnvRes, allowSysRes);

			textAppearanceArray.recycle();
			break;
		}
		case android.R.attr.drawableLeft: {
			EnvRes res = new EnvRes(resid);
			mDrawableLeftEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		case android.R.attr.drawableTop: {
			EnvRes res = new EnvRes(resid);
			mDrawableTopEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		case android.R.attr.drawableRight: {
			EnvRes res = new EnvRes(resid);
			mDrawableRightEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		case android.R.attr.drawableBottom: {
			EnvRes res = new EnvRes(resid);
			mDrawableBottomEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(TV view, TVC call) {
		super.onScheduleSkin(view, call);
		scheduleCompoundDrawable(view, call);
		scheduleTextColor(view, call);
	}

	@Override
	protected void onScheduleFont(TV view, TVC call) {
		super.onScheduleFont(view, call);
		Typeface tf = EnvResourcesHelper.getFontFamily(view.getContext()).getTypeface();
		view.setTypeface(tf);
	}

	private void scheduleCompoundDrawable(TV view, TVC call) {
		Resources res = view.getResources();
		if (mDrawableLeftEnvRes != null || mDrawableTopEnvRes != null || mDrawableRightEnvRes != null || mDrawableBottomEnvRes != null) {
			Drawable drawableLeft = mDrawableLeftEnvRes != null ? res.getDrawable(mDrawableLeftEnvRes.getResid()) : null;
			Drawable drawableTop = mDrawableTopEnvRes != null ? res.getDrawable(mDrawableTopEnvRes.getResid()) : null;
			Drawable drawableRight = mDrawableRightEnvRes != null ? res.getDrawable(mDrawableRightEnvRes.getResid()) : null;
			Drawable drawableBottom = mDrawableBottomEnvRes != null ? res.getDrawable(mDrawableBottomEnvRes.getResid()) : null;
			if (drawableLeft != null || drawableTop != null || drawableRight != null || drawableBottom != null) {
				final int drawablePadding = view.getCompoundDrawablePadding();
				call.scheduleCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
				view.setCompoundDrawablePadding(drawablePadding);
			}
		}
	}

	private void scheduleTextColor(TV view, TVC call) {
		Resources res = view.getResources();
		if (mTextColorHighlightEnvRes != null) {
			int textColorHighlight = res.getColor(mTextColorHighlightEnvRes.getResid());
			call.scheduleHighlightColor(textColorHighlight);
		}
		if (mTextColorEnvRes != null) {
			ColorStateList textColor = res.getColorStateList(mTextColorEnvRes.getResid());
			call.scheduleTextColor(textColor);
		}
		if (mTextColorHintEnvRes != null) {
			ColorStateList textColorHint = res.getColorStateList(mTextColorHintEnvRes.getResid());
			call.scheduleHintTextColor(textColorHint);
		}
		if (mTextColorLinkEnvRes != null) {
			ColorStateList textColorLink = res.getColorStateList(mTextColorLinkEnvRes.getResid());
			call.scheduleLinkTextColor(textColorLink);
		}
	}
}
