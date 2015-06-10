package com.v7lin.android.env.webkit;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class WebViewHelper {

	private WebViewHelper() {
		super();
	}

	public static void setCookie(Context context, String url, String value) {
		if (context != null) {
			CookieSyncManager.createInstance(context);
			CookieManager.getInstance().setCookie(url, value);
			CookieSyncManager.getInstance().sync();
		}
	}

	public static void clearAllCookies(Context context) {
		if (context != null) {
			CookieSyncManager.createInstance(context);
			CookieManager.getInstance().removeAllCookie();
			CookieSyncManager.getInstance().sync();
		}
	}

	public static void removeExpiredCookie(Context context) {
		if (context != null) {
			CookieSyncManager.createInstance(context);
			CookieManager.getInstance().removeExpiredCookie();
			CookieSyncManager.getInstance().sync();
		}
	}

	public static void removeSessionCookie(Context context) {
		if (context != null) {
			CookieSyncManager.createInstance(context);
			CookieSyncManager.getInstance().startSync();
			CookieManager.getInstance().removeSessionCookie();
		}
	}
}
