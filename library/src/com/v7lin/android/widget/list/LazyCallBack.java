package com.v7lin.android.widget.list;

import android.widget.ImageView;

/**
 * 辅助类，桥接 LazyAdapter 和 LazyHolder
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class LazyCallBack {

	private boolean mIsIdle = true;

	public final boolean isIdle() {
		return mIsIdle;
	}

	public final void setIsIdle(boolean isIdle) {
		mIsIdle = isIdle;
	}

	public abstract void scheduleImage(ImageView imageView, String imgUrl, int type, boolean isIdle);

}
