package com.v7lin.android.env.extra;

import android.graphics.drawable.Drawable;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class SkinData {

	private String mSkinName;
	private String mSkinPath;
	private Drawable mSkinBGDraw;
	private int mSkinTextColor;
	private boolean mIsSelected;

	public SkinData(String skinName, String skinPath, Drawable skinBGDraw, int skinTextColor, boolean isSelected) {
		super();
		mSkinName = skinName;
		mSkinPath = skinPath;
		mSkinBGDraw = skinBGDraw;
		mSkinTextColor = skinTextColor;
		mIsSelected = isSelected;
	}

	public String getSkinName() {
		return mSkinName;
	}

	public String getSkinPath() {
		return mSkinPath;
	}

	public Drawable getSkinBGDraw() {
		return mSkinBGDraw;
	}

	public int getSkinTextColor() {
		return mSkinTextColor;
	}

	public boolean isSelected() {
		return mIsSelected;
	}
}
