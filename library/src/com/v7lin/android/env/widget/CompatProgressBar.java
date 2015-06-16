package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.v7lin.android.env.EnvCallback;

/**
 * 采用 Region.Op.XOR 方案会有 BUG，故而变更方案
 * 
 * ProgressDrawable 中使用 ScaleDrawable，在小米 1 上显示不正常
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatProgressBar extends ProgressBar implements EnvCallback {

	private EnvUIChanger<CompatProgressBar> mEnvUIChanger;

	public CompatProgressBar(Context context) {
		this(context, null);
	}

	public CompatProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.progressBarStyle);
	}

	public CompatProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvProgressBarChanger<CompatProgressBar>();
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
