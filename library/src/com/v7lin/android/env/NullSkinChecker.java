package com.v7lin.android.env;

import android.content.Context;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class NullSkinChecker implements SkinChecker {

	private NullSkinChecker() {
		super();
	}

	@Override
	public boolean isSkinValid(Context context, String skinPath) {
		return false;
	}
	
	private static class NullSkinCheckerHolder {
		private static final NullSkinChecker INSTANCE = new NullSkinChecker();
	}

	public static NullSkinChecker getInstance() {
		return NullSkinCheckerHolder.INSTANCE;
	}

}
