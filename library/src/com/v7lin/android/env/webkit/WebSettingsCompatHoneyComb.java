package com.v7lin.android.env.webkit;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebSettings;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class WebSettingsCompatHoneyComb {

	public static void setAllowContentAccess(WebSettings settings, boolean allow) {
		settings.setAllowContentAccess(allow);
	}

	public static void setDisplayZoomControls(WebSettings settings, boolean enabled) {
		settings.setDisplayZoomControls(enabled);
	}
}
