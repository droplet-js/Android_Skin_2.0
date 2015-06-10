package com.v7lin.android.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.AttributeSet;

import com.v7lin.android.env.widget.CompatTextView;

/**
 * 刚刚
 * 1分钟前
 * 1天前
 * 1周前
 * 1月前
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class RelativeTimeTextView extends CompatTextView {

	private static final int TICK_WHAT = 2;

	private long mReferenceTime;
	private CharSequence mJustNow;

	private boolean mVisible;
	private boolean mStarted;
	private boolean mRunning;

	public RelativeTimeTextView(Context context) {
		super(context);
	}

	public RelativeTimeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RelativeTimeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setJustNow(CharSequence justnow) {
		this.mJustNow = justnow;
	}

	public void setReferenceTime(long referenceTime) {
		this.mReferenceTime = referenceTime;
		updateDisplay();
	}

	public void start() {
		mStarted = true;
		updateRunning();
	}

	public void stop() {
		mStarted = false;
		updateRunning();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mVisible = false;
		updateRunning();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		mVisible = visibility == VISIBLE;
		updateRunning();
	}

	private synchronized void updateDisplay() {
		long now = System.currentTimeMillis();
		long difference = now - mReferenceTime;
		CharSequence desc = null;
		if (difference >= 0 && difference <= DateUtils.MINUTE_IN_MILLIS && !TextUtils.isEmpty(mJustNow)) {
			desc = mJustNow;
		} else {
			desc = DateUtils.getRelativeTimeSpanString(mReferenceTime, now, DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);
		}
		setText(desc);
	}

	private void updateRunning() {
		boolean running = mVisible && mStarted;
		if (running != mRunning) {
			if (running) {
				updateDisplay();
				mHandler.sendMessageDelayed(Message.obtain(mHandler, TICK_WHAT), 1000);
			} else {
				mHandler.removeMessages(TICK_WHAT);
			}
			mRunning = running;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message m) {
			if (mRunning) {
				updateDisplay();
				sendMessageDelayed(Message.obtain(this, TICK_WHAT), 1000);
			}
		}
	};
}
