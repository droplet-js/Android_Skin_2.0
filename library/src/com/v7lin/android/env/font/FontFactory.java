package com.v7lin.android.env.font;

import java.io.IOException;
import java.util.Locale;

import android.graphics.Typeface;
import android.text.TextUtils;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class FontFactory {

	public static final String TTF_ENDING = ".ttf";
	public static final String OTF_ENDING = ".otf";

	private static final FontParser TTF_FONT_PARSER = new FontParser() {

		@Override
		public FontFamily parse(String fontPath) {
			FontFamily family = null;
			try {
				TTFParser parser = new TTFParser();
				parser.parse(fontPath);
				String fontName = parser.getFontName();
				Typeface typeFace = Typeface.createFromFile(fontPath);
				family = new FontFamily(fontPath, fontName, typeFace);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return family;
		}
	};

	private static final FontParser OTF_FONT_PARSER = new FontParser() {

		@Override
		public FontFamily parse(String fontPath) {
			return null;
		}
	};

	public static FontFamily parse(String fontPath) {
		FontFamily family = null;
		if (!TextUtils.isEmpty(fontPath)) {
			if (fontPath.toLowerCase(Locale.getDefault()).endsWith(TTF_ENDING)) {
				family = TTF_FONT_PARSER.parse(fontPath);
			} else if (fontPath.toLowerCase(Locale.getDefault()).endsWith(OTF_ENDING)) {
				family = OTF_FONT_PARSER.parse(fontPath);
			}
		}
		return family;
	}
}