package com.v7lin.android.env.font;

import android.graphics.Typeface;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class FontFamily {

	public static final String DEFAULT_FONTPATH = "system";
	public static final FontFamily DEFAULT_FONT = new FontFamily("", "default", Typeface.DEFAULT);

	public static final FontFamily SERIF_FONT = new FontFamily("", "serif", Typeface.SERIF);
	public static final FontFamily SANSSERIF_FONT = new FontFamily("", "sans-serif", Typeface.SANS_SERIF);
	public static final FontFamily MONOSPACE_FONT = new FontFamily("", "monospace", Typeface.MONOSPACE);

	private String mFontPath;
	private String mFontName;
	private Typeface mTypeface;

	public FontFamily(String fontPath, String fontName, Typeface typeFace) {
		mFontPath = fontPath;
		mFontName = fontName;
		mTypeface = typeFace;
	}

	public String getFontPath() {
		return mFontPath;
	}

	public String getFontName() {
		return mFontName;
	}

	public Typeface getTypeface() {
		return mTypeface;
	}
}
