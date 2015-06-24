package com.v7lin.android.env.widget;

import com.android.internal.R;
import com.v7lin.android.env.EnvCallback;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.DatePicker;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatDatePicker extends DatePicker implements EnvCallback {

	private EnvUIChanger<DatePicker> mEnvUIChanger;

	public CompatDatePicker(Context context) {
		this(context, null);
	}

	public CompatDatePicker(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.datePickerStyle);
	}

	public CompatDatePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvFrameLayoutChanger<DatePicker>();
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
