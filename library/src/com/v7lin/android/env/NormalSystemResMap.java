package com.v7lin.android.env;

import android.content.Context;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class NormalSystemResMap implements SystemResMap {

	private NormalSystemResMap() {
		super();
	}

	@Override
	public int mapping(Context context, int resid, String resourcePackageName, String resourceTypeName, String resourceEntryName) {
		return 0;
	}

	private static class NormalSystemResMapHolder {
		private static final NormalSystemResMap INSTANCE = new NormalSystemResMap();
	}

	public static NormalSystemResMap getInstance() {
		return NormalSystemResMapHolder.INSTANCE;
	}
}
