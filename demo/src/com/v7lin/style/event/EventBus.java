package com.v7lin.style.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 基于 {@link LocalBroadcastManager} 实现的事件流
 * 
 * {@link LocalBroadcastManager} 的本质是基于观察者模式设计的单例
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EventBus implements EventConst {

	private Context mContext;
	private final AtomicBoolean mCreate = new AtomicBoolean(false);
	private final AtomicBoolean mStarted = new AtomicBoolean(false);
	private final AtomicBoolean mResumed = new AtomicBoolean(false);

	private final Map<String, EventSession> mLocalEventMap = new HashMap<String, EventSession>();
	private final Map<String, EventSession> mGlobalEventMap = new HashMap<String, EventSession>();

	public EventBus(Context context) {
		super();

		mContext = context;
	}

	public void onCreate() {
		if (mCreate.compareAndSet(false, true)) {
			registerReceiver(EventMode.CREATE_DESTROY);
		}
	}

	public void onStart() {
		if (mStarted.compareAndSet(false, true)) {
			registerReceiver(EventMode.START_STOP);
		}
	}

	public void onResume() {
		if (mResumed.compareAndSet(false, true)) {
			registerReceiver(EventMode.RESUME_PAUSE);
		}
	}

	public void onPause() {
		if (mResumed.compareAndSet(true, false)) {
			unregisterReceiver(EventMode.RESUME_PAUSE);
		}
	}

	public void onStop() {
		if (mStarted.compareAndSet(true, false)) {
			unregisterReceiver(EventMode.START_STOP);
		}
	}

	public void onDestroy() {
		if (mCreate.compareAndSet(true, false)) {
			unregisterReceiver(EventMode.CREATE_DESTROY);
		}
	}

	private void registerReceiver(EventMode mode) {
		EventFilter filter = new EventFilter(mode);
		Collection<EventSession> localSessions = mLocalEventMap.values();
		for (EventSession session : localSessions) {
			session.registerReceiver(mContext, filter, true);
		}
		Collection<EventSession> globalSessions = mGlobalEventMap.values();
		for (EventSession session : globalSessions) {
			session.registerReceiver(mContext, filter, false);
		}
	}

	private void unregisterReceiver(EventMode mode) {
		EventFilter filter = new EventFilter(mode);
		Collection<EventSession> localSessions = mLocalEventMap.values();
		for (EventSession session : localSessions) {
			session.unregisterReceiver(mContext, filter, true);
		}
		Collection<EventSession> globalSessions = mGlobalEventMap.values();
		for (EventSession session : globalSessions) {
			session.unregisterReceiver(mContext, filter, false);
		}
	}

	/**
	 * 注册广播
	 */
	public void registerEventReceiver(boolean isLocal, String eventKey, EventMode mode, EventReceiver receiver, String... actions) {
		EventSession session = new EventSession(eventKey, mode, receiver, actions);
		EventFilter filter = new EventFilter(mode);
		Map<String, EventSession> eventMap = isLocal ? mLocalEventMap : mGlobalEventMap;
		EventSession oldSession = eventMap.put(eventKey, session);
		if (oldSession != null) {
			oldSession.unregisterReceiver(mContext, null, isLocal);// 不需要过滤器
		}
		session.registerReceiver(mContext, filter, isLocal);
	}

	/**
	 * 注销广播
	 */
	public void unregisterEventReceiver(boolean isLocal, String eventKey) {
		Map<String, EventSession> eventMap = isLocal ? mLocalEventMap : mGlobalEventMap;
		EventSession session = eventMap.remove(eventKey);
		if (session != null) {
			session.unregisterReceiver(mContext, null, isLocal);// 不需要过滤器
		}
	}

	/**
	 * 发送广播
	 */
	public static void sendEvent(Context context, String action, Bundle extras, boolean isLocal) {
		Intent intent = new Intent(action);
		if (extras != null) {
			intent.putExtra(KEY_BUNDLE_EVENT, extras);
		}
		if (isLocal) {
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
		} else {
			context.sendBroadcast(intent);
		}
	}
}
