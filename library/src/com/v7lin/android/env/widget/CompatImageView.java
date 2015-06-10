package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatImageView extends ImageView implements EnvCallback {

	private EnvUIChanger<ImageView> mEnvUIChanger;

	public CompatImageView(Context context) {
		this(context, null);
	}

	public CompatImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CompatImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvImageViewChanger<ImageView>();
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
