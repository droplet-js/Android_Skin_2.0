package com.v7lin.android.env.widget;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class EnvViewChanger<V extends View> extends EnvUIChanger<V> {
	
	private static final int[] ATTRS = {
			//
			android.R.attr.scrollbarTrackHorizontal,
			//
			android.R.attr.scrollbarThumbHorizontal,
			//
			android.R.attr.scrollbarTrackVertical,
			//
			android.R.attr.scrollbarThumbVertical,
			//
			android.R.attr.background,
			//
			android.R.attr.padding,
			//
			android.R.attr.paddingLeft,
			//
			android.R.attr.paddingTop,
			//
			android.R.attr.paddingRight,
			//
			android.R.attr.paddingBottom,
			//
			android.R.attr.minWidth,
			//
			android.R.attr.minHeight
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mBackgroundEnvRes;
	private EnvRes mPaddingEnvRes;
	private EnvRes mPaddingLeftEnvRes;
	private EnvRes mPaddingTopEnvRes;
	private EnvRes mPaddingRightEnvRes;
	private EnvRes mPaddingBottomEnvRes;

	private EnvRes mMinWidthEnvRes;
	private EnvRes mMinHeightEnvRes;

	private EnvRes mScrollbarTrackHorizontalEnvRes;
	private EnvRes mScrollbarThumbHorizontalEnvRes;
	private EnvRes mScrollbarTrackVerticalEnvRes;
	private EnvRes mScrollbarThumbVerticalEnvRes;

	public EnvViewChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mBackgroundEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.background), allowSysRes);
		mPaddingEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.padding), allowSysRes);
		mPaddingLeftEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.paddingLeft), allowSysRes);
		mPaddingTopEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.paddingTop), allowSysRes);
		mPaddingRightEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.paddingRight), allowSysRes);
		mPaddingBottomEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.paddingBottom), allowSysRes);

		mMinWidthEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.minWidth), allowSysRes);
		mMinHeightEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.minHeight), allowSysRes);

		mScrollbarTrackHorizontalEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.scrollbarTrackHorizontal), allowSysRes);
		mScrollbarThumbHorizontalEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.scrollbarThumbHorizontal), allowSysRes);
		mScrollbarTrackVerticalEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.scrollbarTrackVertical), allowSysRes);
		mScrollbarThumbVerticalEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.scrollbarThumbVertical), allowSysRes);
		array.recycle();
	}

	@Override
	public final void scheduleSkin(V view) {
		onScheduleSkin(view);
	}

	protected void onScheduleSkin(V view) {
		scheduleBackground(view);
		scheduleMinParams(view);
		scheduleScrollBar(view);
	}

	@Override
	public final void scheduleFont(V view) {
		onScheduleFont(view);
	}

	protected void onScheduleFont(V view) {

	}

	private void scheduleBackground(V view) {
		Resources res = view.getResources();
		if (mBackgroundEnvRes != null) {
			Drawable drawable = res.getDrawable(mBackgroundEnvRes.getResid());
			if (drawable != null) {
				final int leftPadding = view.getPaddingLeft();
				final int topPadding = view.getPaddingTop();
				final int rightPadding = view.getPaddingRight();
				final int bottomPadding = view.getPaddingBottom();
				view.setBackgroundDrawable(drawable);
				view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
			}
		}

		if (mPaddingEnvRes != null) {
			final int padding = res.getDimensionPixelSize(mPaddingEnvRes.getResid());
			view.setPadding(padding, padding, padding, padding);
		} else if (mPaddingLeftEnvRes != null || mPaddingTopEnvRes != null || mPaddingRightEnvRes != null || mPaddingBottomEnvRes != null) {
			final int leftPadding = mPaddingLeftEnvRes != null ? res.getDimensionPixelSize(mPaddingLeftEnvRes.getResid()) : view.getPaddingLeft();
			final int topPadding = mPaddingTopEnvRes != null ? res.getDimensionPixelSize(mPaddingTopEnvRes.getResid()) : view.getPaddingTop();
			final int rightPadding = mPaddingRightEnvRes != null ? res.getDimensionPixelSize(mPaddingRightEnvRes.getResid()) : view.getPaddingRight();
			final int bottomPadding = mPaddingBottomEnvRes != null ? res.getDimensionPixelSize(mPaddingBottomEnvRes.getResid()) : view.getPaddingBottom();
			view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
		}
	}

	private void scheduleMinParams(V view) {
		Resources res = view.getResources();
		if (mMinWidthEnvRes != null) {
			final int minWidth = res.getDimensionPixelSize(mMinWidthEnvRes.getResid());
			view.setMinimumWidth(minWidth);
		}
		if (mMinHeightEnvRes != null) {
			final int minHeight = res.getDimensionPixelSize(mMinHeightEnvRes.getResid());
			view.setMinimumHeight(minHeight);
		}
	}

	private void scheduleScrollBar(V view) {
		try {
			Resources res = view.getResources();
			if (mScrollbarTrackHorizontalEnvRes != null || mScrollbarThumbHorizontalEnvRes != null || mScrollbarTrackVerticalEnvRes != null || mScrollbarThumbVerticalEnvRes != null) {
				Field mScrollCacheField = View.class.getDeclaredField("mScrollCache");
				mScrollCacheField.setAccessible(true);
				Object mScrollCache = mScrollCacheField.get(view);
				if (mScrollCache != null) {
					Field scrollBarField = mScrollCache.getClass().getDeclaredField("scrollBar");
					scrollBarField.setAccessible(true);
					Object scrollBar = scrollBarField.get(mScrollCache);
					if (scrollBar != null) {
						if (mScrollbarTrackHorizontalEnvRes != null) {
							Drawable horizontalTrackDrawable = res.getDrawable(mScrollbarTrackHorizontalEnvRes.getResid());
							if (horizontalTrackDrawable != null) {
								Method setHorizontalTrackDrawableMethod = scrollBar.getClass().getDeclaredMethod("setHorizontalTrackDrawable", Drawable.class);
								setHorizontalTrackDrawableMethod.setAccessible(true);
								setHorizontalTrackDrawableMethod.invoke(scrollBar, horizontalTrackDrawable);
							}
						}
						if (mScrollbarThumbHorizontalEnvRes != null) {
							Drawable horizontalThumbDrawable = res.getDrawable(mScrollbarThumbHorizontalEnvRes.getResid());
							if (horizontalThumbDrawable != null) {
								Method setHorizontalThumbDrawableMethod = scrollBar.getClass().getDeclaredMethod("setHorizontalThumbDrawable", Drawable.class);
								setHorizontalThumbDrawableMethod.setAccessible(true);
								setHorizontalThumbDrawableMethod.invoke(scrollBar, horizontalThumbDrawable);
							}
						}
						if (mScrollbarTrackVerticalEnvRes != null) {
							Drawable verticalTrackDrawable = res.getDrawable(mScrollbarTrackVerticalEnvRes.getResid());
							if (verticalTrackDrawable != null) {
								Method setVerticalTrackDrawableMethod = scrollBar.getClass().getDeclaredMethod("setVerticalTrackDrawable", Drawable.class);
								setVerticalTrackDrawableMethod.setAccessible(true);
								setVerticalTrackDrawableMethod.invoke(scrollBar, verticalTrackDrawable);
							}
						}
						if (mScrollbarThumbVerticalEnvRes != null) {
							Drawable verticalThumbDrawable = res.getDrawable(mScrollbarThumbVerticalEnvRes.getResid());
							if (verticalThumbDrawable != null) {
								Method setVerticalThumbDrawableMethod = scrollBar.getClass().getDeclaredMethod("setVerticalThumbDrawable", Drawable.class);
								setVerticalThumbDrawableMethod.setAccessible(true);
								setVerticalThumbDrawableMethod.invoke(scrollBar, verticalThumbDrawable);
							}
						}
					}
				}
			}

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
}
