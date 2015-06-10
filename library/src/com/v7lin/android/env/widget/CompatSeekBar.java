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
	private OnSeekBarChangeListener mDelegateOnSeekBarChangeListener;

	public CompatSeekBar(Context context) {
		this(context, null);
	}

	public CompatSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.seekBarStyle);
	}

	public CompatSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mEnvUIChanger = new EnvCompatSeekBarChanger<CompatSeekBar>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, false);

		setOnSeekBarChangeListener(mOnSeekBarChangeListener);
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

	class EnvCompatSeekBarChanger<CSB extends CompatSeekBar> extends EnvAbsSeekBarChanger<CSB> {

		public EnvCompatSeekBarChanger() {
			super();
		}

		@Override
		public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
			super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
		}
	}

	private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if (mDelegateOnSeekBarChangeListener != null) {
				mDelegateOnSeekBarChangeListener.onStartTrackingTouch(seekBar);
			}
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (mDelegateOnSeekBarChangeListener != null) {
				mDelegateOnSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
			}
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (mDelegateOnSeekBarChangeListener != null) {
				mDelegateOnSeekBarChangeListener.onStopTrackingTouch(seekBar);
			}
		}
	};

	@Override
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		if (l == mOnSeekBarChangeListener) {
			super.setOnSeekBarChangeListener(l);
		} else {
			mDelegateOnSeekBarChangeListener = l;
		}
	}
}
