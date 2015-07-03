package com.v7lin.android.env.extra;

import com.v7lin.android.env.font.FontFamily;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class FontData {

	private String mFontAlias;
	private FontFamily mFontFamily;
	private boolean mIsSelected;

	public FontData(String fontAlias, FontFamily fontFamily, boolean isSelected) {
		super();
		mFontAlias = fontAlias;
		mFontFamily = fontFamily;
		mIsSelected = isSelected;
	}

	public String getFontAlias() {
		return mFontAlias;
	}

	public FontFamily getFontFamily() {
		return mFontFamily;
	}

	public boolean isSelected() {
		return mIsSelected;
	}
}
