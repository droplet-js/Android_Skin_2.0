package com.v7lin.style.event;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public enum EventMode {
	// onCreate 注册，onDestroy 注销
	CREATE_DESTROY,
	// onResume 注册，onPause 注销
	RESUME_PAUSE,
	// onStart 注册，onStop 注销
	START_STOP;
}
