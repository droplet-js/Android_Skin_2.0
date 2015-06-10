package com.v7lin.style.event;

import java.util.concurrent.atomic.AtomicBoolean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
class EventSession implements EventConst {

	final String mEventKey;
	final EventMode mMode;
	final EventReceiver mReceiver;
	final String[] mActions;

	BroadcastReceiver mBroadcastReceiver;
	IntentFilter mIntentFilter;

	final AtomicBoolean mRegister = new AtomicBoolean(false);

	EventSession(String eventKey, EventMode mode, EventReceiver receiver, String... actions) {
		super();
		mEventKey = eventKey;
		mMode = mode;
		mReceiver = receiver;
		mActions = actions;

		ensureBroadcastReceiver();
		ensureIntentFilter();
	}

	void ensureBroadcastReceiver() {
		if (mBroadcastReceiver == null) {
			mBroadcastReceiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					mReceiver.onEvent(intent.getAction(), intent.getBundleExtra(KEY_BUNDLE_EVENT));
				}
			};
		}
	}

	public void ensureIntentFilter() {
		if (mIntentFilter == null) {
			mIntentFilter = new IntentFilter();
			final int len = mActions != null ? mActions.length : 0;
			for (int i = 0; i < len; i ++) {
				mIntentFilter.addAction(mActions[i]);
			}
		}
	}

	void registerReceiver(Context context, EventFilter filter, boolean isLocal) {
		if (filter == null || filter.accept(mMode)) {
			if (mRegister.compareAndSet(false, true)) {
				if (isLocal) {
					LocalBroadcastManager.getInstance(context).registerReceiver(mBroadcastReceiver, mIntentFilter);
				} else {
					context.registerReceiver(mBroadcastReceiver, mIntentFilter);
				}
			}
		}
	}

	void unregisterReceiver(Context context, EventFilter filter, boolean isLocal) {
		if (filter == null || filter.accept(mMode)) {
			if (mRegister.compareAndSet(true, false)) {
				if (isLocal) {
					LocalBroadcastManager.getInstance(context).unregisterReceiver(mBroadcastReceiver);
				} else {
					context.unregisterReceiver(mBroadcastReceiver);
				}
			}
		}
	}

	@Override
	public int hashCode() {
		return !TextUtils.isEmpty(mEventKey) ? mEventKey.hashCode() : "null".hashCode();// super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		boolean result = super.equals(o);
		if (!result) {
			if (o != null && o instanceof EventSession) {
				EventSession other = (EventSession) o;
				result = TextUtils.equals(mEventKey, other.mEventKey);
			}
		}
		return result;
	}
}
