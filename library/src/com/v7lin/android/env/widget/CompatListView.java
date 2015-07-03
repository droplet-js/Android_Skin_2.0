package com.v7lin.android.env.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatListView extends ListView implements XListViewCall, EnvCallback {

	private static final boolean ALLOW_SYSRES = false;

	private EnvUIChanger<ListView, XListViewCall> mEnvUIChanger;

	public CompatListView(Context context) {
		this(context, null);
	}

	public CompatListView(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.listViewStyle);
	}

	public CompatListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvListViewChanger<ListView, XListViewCall>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, ALLOW_SYSRES, isInEditMode());
	}

	@Override
	public void setDivider(Drawable divider) {
		super.setDivider(divider);

		applyAttrDivider(0);
	}

	private void applyAttrDivider(int resid) {
		applyAttr(getContext(), android.R.attr.divider, resid);
	}

	@Override
	public void setSelector(int resID) {
		super.setSelector(resID);

		applyAttrSelector(resID);
	}

	@Override
	public void setSelector(Drawable sel) {
		super.setSelector(sel);

		applyAttrSelector(0);
	}

	private void applyAttrSelector(int resid) {
		applyAttr(getContext(), android.R.attr.listSelector, resid);
	}

	@Override
	public void setCacheColorHint(int color) {
		super.setCacheColorHint(color);

		applyAttrCacheColorHint(0);
	}

	private void applyAttrCacheColorHint(int resid) {
		applyAttr(getContext(), android.R.attr.cacheColorHint, resid);
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
			mEnvUIChanger.applyAttr(context, attr, resid, ALLOW_SYSRES, isInEditMode());
		}
	}

	@Override
	public void scheduleDivider(Drawable divider) {
		super.setDivider(divider);
	}

	@Override
	public void scheduleSelector(Drawable sel) {
		super.setSelector(sel);
	}

	@Override
	public void scheduleCacheColorHint(int color) {
		super.setCacheColorHint(color);
	}

	@Override
	public void scheduleBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);
	}

	@Override
	public void scheduleSkin() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this, isInEditMode());
		}
	}

	@Override
	public void scheduleFont() {
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleFont(this, this, isInEditMode());
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mEnvUIChanger != null) {
			mEnvUIChanger.scheduleSkin(this, this, isInEditMode());
			mEnvUIChanger.scheduleFont(this, this, isInEditMode());
		}
	}
}
