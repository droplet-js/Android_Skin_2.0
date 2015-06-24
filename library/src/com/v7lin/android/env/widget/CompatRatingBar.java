package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatRatingBar extends RatingBar implements EnvCallback {

	private EnvUIChanger<RatingBar> mEnvUIChanger;

	public CompatRatingBar(Context context) {
		this(context, null);
	}

	public CompatRatingBar(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.ratingBarStyle);
	}

	public CompatRatingBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvAbsSeekBarChanger<RatingBar>();
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
