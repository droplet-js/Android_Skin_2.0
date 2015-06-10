package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatListView extends ListView implements EnvCallback {

	private EnvUIChanger<ListView> mEnvUIChanger;

	public CompatListView(Context context) {
		this(context, null);
	}

	public CompatListView(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.listViewStyle);
	}

	public CompatListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvListViewChanger<ListView>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, false);
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
}
