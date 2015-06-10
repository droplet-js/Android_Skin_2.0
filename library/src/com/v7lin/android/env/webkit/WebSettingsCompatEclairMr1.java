package com.v7lin.android.env.webkit;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;

import com.v7lin.android.env.webkit.WebSettingsCompat.ZoomDensityCompat;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
@SuppressWarnings("deprecation")
class WebSettingsCompatEclairMr1 {

	public static void setDefaultZoom(WebSettings settings, ZoomDensityCompat zoom) {
		switch (zoom) {
		case FAR: {
			settings.setDefaultZoom(ZoomDensity.FAR);
			break;
		}
		case MEDIUM: {
			settings.setDefaultZoom(ZoomDensity.MEDIUM);
			break;
		}
		case CLOSE: {
			settings.setDefaultZoom(ZoomDensity.CLOSE);
			break;
		}
		default: {
			break;
		}
		}
	}

	public static void setDomStorageEnabled(WebSettings settings, boolean flag) {
		settings.setDomStorageEnabled(flag);
	}

	public static void setAppCacheEnabled(WebSettings settings, boolean flag) {
		settings.setAppCacheEnabled(flag);
	}

	public static void setAppCachePath(WebSettings settings, String appCachePath) {
		settings.setAppCachePath(appCachePath);
	}

	public static void setAppCacheMaxSize(WebSettings settings, long appCacheMaxSize) {
		settings.setAppCacheMaxSize(appCacheMaxSize);
	}

	public static void setLoadWithOverviewMode(WebSettings settings, boolean overview) {
		settings.setLoadWithOverviewMode(overview);
	}
}
