package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.v7lin.android.env.EnvCallback;
import com.v7lin.android.env.EnvResourcesManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class EnvUIChanger<UI, UIC> {

	private Context mContext;
	private String mSkinPath;
	private String mFontPath;
	private final AtomicBoolean mInitSkinFlag = new AtomicBoolean(false);
	private final AtomicBoolean mInitFontFlag = new AtomicBoolean(false);

	public EnvUIChanger(Context context) {
		super();
		mContext = context;
		mSkinPath = EnvResourcesManager.getGlobal().getSkinPath(context);
		mFontPath = EnvResourcesManager.getGlobal().getFontPath(context);
	}

	public final boolean isSkinChanged() {
		return EnvResourcesManager.getGlobal().isSkinChanged(mContext, mSkinPath);
	}

	public final boolean isFontChanged() {
		return EnvResourcesManager.getGlobal().isFontChanged(mContext, mFontPath);
	}

	public final void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes, boolean isInEditMode) {
		if (!isInEditMode) {
			onApplyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		}
	}
	
	protected abstract void onApplyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes);

	public final void applyAttr(Context context, int attr, int resid, boolean allowSysRes, boolean isInEditMode) {
		if (!isInEditMode) {
			onApplyAttr(context, attr, resid, allowSysRes);
		}
	}
	
	protected abstract void onApplyAttr(Context context, int attr, int resid, boolean allowSysRes);

	public final void scheduleSkin(UI ui, UIC call, boolean isInEditMode) {
		if (!isInEditMode && (isSkinChanged() || mInitSkinFlag.compareAndSet(false, true))) {
			onScheduleSkin(ui, call);
		}
	}

	protected abstract void onScheduleSkin(UI ui, UIC call);

	public final void scheduleFont(UI ui, UIC call, boolean isInEditMode) {
		if (!isInEditMode && (isFontChanged() || mInitFontFlag.compareAndSet(false, true))) {
			onScheduleFont(ui, call);
		}
	}

	protected abstract void onScheduleFont(UI ui, UIC call);

	protected final void scheduleViewSkin(View view) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleSkin();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupSkin((ViewGroup) view);
			}
		}
	}

	protected final void scheduleViewGroupSkin(ViewGroup group) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewSkin(child);
			}
		}
	}

	protected final void scheduleViewFont(View view) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleFont();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupFont((ViewGroup) view);
			}
		}
	}

	protected final void scheduleViewGroupFont(ViewGroup group) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewFont(child);
			}
		}
	}
}
