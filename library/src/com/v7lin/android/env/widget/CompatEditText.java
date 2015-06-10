package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.v7lin.android.env.EnvCallback;

/**
 * http://chiuki.github.io/advanced-android-textview/#/
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatEditText extends EditText implements EnvCallback {

	private EnvUIChanger<EditText> mEnvUIChanger;

	public CompatEditText(Context context) {
		this(context, null);
	}

	public CompatEditText(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.editTextStyle);
	}

	public CompatEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvTextViewChanger<EditText>();
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
