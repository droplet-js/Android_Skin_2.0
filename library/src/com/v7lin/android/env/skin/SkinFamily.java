package com.v7lin.android.env.skin;

import android.content.res.Resources;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class SkinFamily {

	private String mSkinPath;
	private String mSkinPkg;
	private Resources mResources;

	public SkinFamily(String skinPath, String skinPkg, Resources resources) {
		super();
		mSkinPath = skinPath;
		mSkinPkg = skinPkg;
		mResources = resources;
	}

	public String getSkinPath() {
		return mSkinPath;
	}

	public String getSkinPkg() {
		return mSkinPkg;
	}

	public Resources getResources() {
		return mResources;
	}
}
