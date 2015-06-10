package com.v7lin.android.env.widget;

import com.v7lin.android.env.EnvCallback;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatCheckedTextView extends CheckedTextView implements EnvCallback {

	private EnvUIChanger<CheckedTextView> mEnvUIChanger;

	public CompatCheckedTextView(Context context) {
		this(context, null);
	}

	public CompatCheckedTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CompatCheckedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvCheckedTextViewChanger<CheckedTextView>();
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
