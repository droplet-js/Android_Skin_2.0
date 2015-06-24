package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import com.android.internal.R;
import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatAutoCompleteTextView extends AutoCompleteTextView implements EnvCallback {

	private EnvUIChanger<AutoCompleteTextView> mEnvUIChanger;

	public CompatAutoCompleteTextView(Context context) {
		this(context, null);
	}

	public CompatAutoCompleteTextView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.autoCompleteTextViewStyle);
	}

	public CompatAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvAutoCompleteTextViewChanger<AutoCompleteTextView>();
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
