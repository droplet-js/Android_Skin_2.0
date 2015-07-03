package com.v7lin.android.env;

import android.content.Context;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class NullResMap implements SystemResMap {

	private NullResMap() {
		super();
	}

	@Override
	public int mapping(Context context, int resid, String resourcePackageName, String resourceTypeName, String resourceEntryName) {
		return 0;
	}

	private static class NullResMapHolder {
		private static final NullResMap INSTANCE = new NullResMap();
	}

	public static NullResMap getInstance() {
		return NullResMapHolder.INSTANCE;
	}
}
