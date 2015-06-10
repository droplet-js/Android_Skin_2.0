package com.v7lin.android.env.widget;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.android.internal.R;
import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvProgressBarChanger<PB extends ProgressBar> extends EnvViewChanger<PB> {

	private EnvRes mProgressDrawableEnvRes;
	private EnvRes mMinWidthEnvRes;
	private EnvRes mMaxWidthEnvRes;
	private EnvRes mMinHeightEnvRes;
	private EnvRes mMaxHeightEnvRes;
	private EnvRes mIndeterminateDrawable;

	public EnvProgressBarChanger() {
		super();
	}

	@Override
	public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, R.styleable.ProgressBar, defStyleAttr, defStyleRes);
		mIndeterminateDrawable = array.getEnvRes(com.android.internal.R.styleable.ProgressBar_indeterminateDrawable, allowSysRes);
		mProgressDrawableEnvRes = array.getEnvRes(com.android.internal.R.styleable.ProgressBar_progressDrawable, allowSysRes);
		mMinWidthEnvRes = array.getEnvRes(com.android.internal.R.styleable.ProgressBar_minWidth, allowSysRes);
		mMaxWidthEnvRes = array.getEnvRes(com.android.internal.R.styleable.ProgressBar_maxWidth, allowSysRes);
		mMinHeightEnvRes = array.getEnvRes(com.android.internal.R.styleable.ProgressBar_minHeight, allowSysRes);
		mMaxHeightEnvRes = array.getEnvRes(com.android.internal.R.styleable.ProgressBar_maxHeight, allowSysRes);
		array.recycle();
	}

	@Override
	protected void onScheduleSkin(PB view) {
		super.onScheduleSkin(view);
		scheduleIndeterminateDrawable(view);
		scheduleProgressDrawable(view);
		scheduleLayout(view);
	}

	private void scheduleIndeterminateDrawable(PB view) {
		Resources res = view.getResources();
		if (mIndeterminateDrawable != null) {
			Drawable drawable = res.getDrawable(mIndeterminateDrawable.getResid());
			if (drawable != null) {
				final int left = 0;
				final int top = 0;
				final int right = drawable.getIntrinsicWidth();
				final int bottom = drawable.getIntrinsicHeight();
				drawable.setBounds(left, top, right, bottom);
				view.setIndeterminateDrawable(drawable);
			}
		}
	}

	private void scheduleProgressDrawable(PB view) {
		Resources res = view.getResources();
		if (mProgressDrawableEnvRes != null) {
			Drawable drawable = res.getDrawable(mProgressDrawableEnvRes.getResid());
			if (drawable != null) {
				view.setProgressDrawable(drawable);
				forceRefreshProgress(view);
			}
		}
	}

	private void forceRefreshProgress(PB view) {
		try {
			int progress = view.getProgress();
			Method mRefreshProgressMethod = ProgressBar.class.getDeclaredMethod("refreshProgress", new Class[] { int.class, int.class, Boolean.TYPE });
			mRefreshProgressMethod.setAccessible(true);
			mRefreshProgressMethod.invoke(view, com.android.internal.R.id.progress, progress, false);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void scheduleLayout(PB view) {
		Resources res = view.getResources();
		if (mMinWidthEnvRes != null || mMaxWidthEnvRes != null || mMinHeightEnvRes != null || mMaxHeightEnvRes != null) {
			if (mMinWidthEnvRes != null) {
				final int mMinWidth = res.getDimensionPixelSize(mMinWidthEnvRes.getResid());
				invokeIntValue(view, "mMinWidth", mMinWidth);
			}
			if (mMaxWidthEnvRes != null) {
				final int mMaxWidth = res.getDimensionPixelSize(mMaxWidthEnvRes.getResid());
				invokeIntValue(view, "mMaxWidth", mMaxWidth);
			}
			if (mMinHeightEnvRes != null) {
				final int mMinHeight = res.getDimensionPixelSize(mMinHeightEnvRes.getResid());
				invokeIntValue(view, "mMinHeight", mMinHeight);
			}
			if (mMaxHeightEnvRes != null) {
				final int mMaxHeight = res.getDimensionPixelSize(mMaxHeightEnvRes.getResid());
				invokeIntValue(view, "mMaxHeight", mMaxHeight);
			}
			view.requestLayout();
		}
	}

	private void invokeIntValue(PB view, String fieldName, int value) {
		try {
			Field field = ProgressBar.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.setInt(view, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}
