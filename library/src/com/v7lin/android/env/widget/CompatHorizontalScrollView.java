package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatHorizontalScrollView extends HorizontalScrollView implements EnvCallback {

	private EnvUIChanger<HorizontalScrollView> mEnvUIChanger;

	public CompatHorizontalScrollView(Context context) {
		this(context, null);
	}

	public CompatHorizontalScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.horizontalScrollViewStyle);
	}

	public CompatHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvFrameLayoutChanger<HorizontalScrollView>();
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
