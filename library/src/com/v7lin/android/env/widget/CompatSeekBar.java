package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.v7lin.android.env.EnvCallback;

/**
 * ProgressDrawable 中使用 ScaleDrawable，在小米 1 上显示不正常
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatSeekBar extends SeekBar implements EnvCallback {

	private EnvUIChanger<CompatSeekBar> mEnvUIChanger;

	public CompatSeekBar(Context context) {
		this(context, null);
	}

	public CompatSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.seekBarStyle);
	}

	public CompatSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvAbsSeekBarChanger<CompatSeekBar>();
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
