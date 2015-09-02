package com.v7lin.android.env;

import android.content.Context;
import android.content.res.Resources;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public interface SystemResMap {

	/**
	 * 该函数内不能执行以下函数，否则会陷入死循环，导致函数堆栈溢出
	 * 
	 * {@link Resources#getResourceName(int)}
	 * {@link Resources#getResourcePackageName(int)}
	 * {@link Resources#getResourceTypeName(int)}
	 * {@link Resources#getResourceEntryName(int)}
	 */
	public int mapping(Context context, int resid, String resourcePackageName, String resourceTypeName, String resourceEntryName);
}
