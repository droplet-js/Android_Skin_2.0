package com.v7lin.android.env;

import android.content.Context;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class NormalEnvSetup implements EnvSetup {
	
	private String mSkinPath;
	private String mFontPath;

	public NormalEnvSetup(String skinPath, String fontPath) {
		super();
		this.mSkinPath = skinPath;
		this.mFontPath = fontPath;
	}

	@Override
	public String getSkinPath(Context context) {
		return mSkinPath;
	}

	@Override
	public String getFontPath(Context context) {
		return mFontPath;
	}
}
