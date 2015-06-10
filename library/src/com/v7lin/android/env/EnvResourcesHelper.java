package com.v7lin.android.env;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.v7lin.android.env.font.FontFamily;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvResourcesHelper {

	public static Resources newThirdResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
		// 由于这里的资源都是非系统级别的，不会被缓存到Resources的static成员常量里
		// 即Resources的startPreloading和finishPreloading已在ZygoteInit中被调用完毕，一些启动所需的系统资源被缓存到Resources的static成员常量里
		// 所以可以不用EnvThirdResources
		return new /* EnvThird */Resources(assets, metrics, config);
	}

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
