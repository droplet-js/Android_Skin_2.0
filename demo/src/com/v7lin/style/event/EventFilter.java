package com.v7lin.style.event;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
class EventFilter {

	private EventMode mMode;

	public EventFilter(EventMode mode) {
		super();
		mMode = mode;
	}

	public boolean accept(EventMode mode) {
		return mode == null || mode.equals(mMode);
	}
}
