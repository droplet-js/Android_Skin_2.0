package com.v7lin.android.env.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TimePicker;

import com.android.internal.R;
import com.v7lin.android.env.EnvCallback;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatTimePicker extends TimePicker implements EnvCallback {

	private EnvUIChanger<TimePicker> mEnvUIChanger;

	public CompatTimePicker(Context context) {
		this(context, null);
	}

	public CompatTimePicker(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.timePickerStyle);
	}

	public CompatTimePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvFrameLayoutChanger<TimePicker>();
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
