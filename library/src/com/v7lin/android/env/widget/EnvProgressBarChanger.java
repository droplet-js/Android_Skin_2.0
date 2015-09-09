package com.v7lin.android.env.widget;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvProgressBarChanger<PB extends ProgressBar, PBC extends XProgressBarCall> extends EnvViewChanger<PB, PBC> {
	
	private static final int[] ATTRS = {
			//
			android.R.attr.indeterminateDrawable,
			//
			android.R.attr.progressDrawable
	};

	static {
		Arrays.sort(ATTRS);
	}

	private EnvRes mIndeterminateDrawableEnvRes;
	private EnvRes mProgressDrawableEnvRes;

	public EnvProgressBarChanger(Context context) {
		super(context);
	}

	@Override
	protected void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
		super.onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, ATTRS, defStyleAttr, defStyleRes);
		mIndeterminateDrawableEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.indeterminateDrawable), allowSysRes);
		mProgressDrawableEnvRes = array.getEnvRes(Arrays.binarySearch(ATTRS, android.R.attr.progressDrawable), allowSysRes);
		array.recycle();
	}

	@Override
	protected void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes) {
		super.onApplyAttr(context, attr, resid, allowSysRes);
		
		switch (attr) {
		case android.R.attr.indeterminateDrawable: {
			EnvRes res = new EnvRes(resid);
			mIndeterminateDrawableEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		case android.R.attr.progressDrawable: {
			EnvRes res = new EnvRes(resid);
			mProgressDrawableEnvRes = res.isValid(context, allowSysRes) ? res : null;
			break;
		}
		default: {
			break;
		}
		}
	}

	@Override
	protected void onScheduleSkin(PB view, PBC call) {
		super.onScheduleSkin(view, call);
		scheduleIndeterminateDrawable(view, call);
		scheduleProgressDrawable(view, call);
	}

	private void scheduleIndeterminateDrawable(PB view, PBC call) {
		Resources res = view.getResources();
		if (mIndeterminateDrawableEnvRes != null) {
			Drawable drawable = res.getDrawable(mIndeterminateDrawableEnvRes.getResid());
			if (drawable != null) {
				final int left = 0;
				final int top = 0;
				final int right = drawable.getIntrinsicWidth();
				final int bottom = drawable.getIntrinsicHeight();
				drawable.setBounds(left, top, right, bottom);
				call.scheduleIndeterminateDrawable(drawable);
			}
		}
	}

	private void scheduleProgressDrawable(PB view, PBC call) {
		Resources res = view.getResources();
		if (mProgressDrawableEnvRes != null) {
			Drawable drawable = res.getDrawable(mProgressDrawableEnvRes.getResid());
			if (drawable != null) {
				call.scheduleProgressDrawable(drawable);
				forceRefreshProgress(view, call);
			}
		}
	}

	private void forceRefreshProgress(PB view, PBC call) {
		try {
			int progress = view.getProgress();
			Method mRefreshProgressMethod = ProgressBar.class.getDeclaredMethod("refreshProgress", new Class[] { int.class, int.class, Boolean.TYPE });
			mRefreshProgressMethod.setAccessible(true);
//			mRefreshProgressMethod.invoke(view, com.android.internal.R.id.progress, progress, false);
			mRefreshProgressMethod.invoke(view, InternalTransfer.transferId(view.getContext(), "progress"), progress, false);
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
}
