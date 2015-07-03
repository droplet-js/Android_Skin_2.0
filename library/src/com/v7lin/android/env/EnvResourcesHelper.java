package com.v7lin.android.env;

import android.content.Context;
import android.content.res.Resources;

import com.v7lin.android.env.font.FontFamily;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvResourcesHelper {

	public static FontFamily getFontFamily(Context context) {
		FontFamily fontFamily = null;
		Resources res = context.getResources();
		if (res instanceof EnvResourcesWrapper) {
			fontFamily = ((EnvResourcesWrapper) res).getFontFamily();
		}
		if (fontFamily == null) {
			fontFamily = FontFamily.DEFAULT_FONT;
		}
		return fontFamily;
	}
}
