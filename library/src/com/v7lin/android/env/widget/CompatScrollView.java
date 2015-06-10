package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatScrollView extends ScrollView implements EnvCallback {

	private EnvUIChanger<ScrollView> mEnvUIChanger;

	public CompatScrollView(Context context) {
		this(context, null);
	}

	public CompatScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.scrollViewStyle);
	}

	public CompatScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvFrameLayoutChanger<ScrollView>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, false);
		mEnvUIChanger.scheduleSkin(this);
		mEnvUIChanger.scheduleFont(this);
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
