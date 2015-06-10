package com.v7lin.android.env.webkit;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebSettings;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
class WebSettingsCompatJellyBean {

	public static void setAllowFileAccessFromFileURLs(WebSettings settings, boolean flag) {
		settings.setAllowFileAccessFromFileURLs(flag);
	}

	public static void setAllowUniversalAccessFromFileURLs(WebSettings settings, boolean flag) {
		settings.setAllowUniversalAccessFromFileURLs(flag);
	}
}
