package com.v7lin.android.env.skin;

import android.content.res.Resources;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class SkinFamily {

	private String mSkinPath;
	private Resources mResources;

	public SkinFamily(String skinPath, Resources resources) {
		super();
		this.mSkinPath = skinPath;
		this.mResources = resources;
	}

	public String getSkinPath() {
		return mSkinPath;
	}

	public Resources getResources() {
		return mResources;
	}
}
