package com.v7lin.android.env.webkit;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.CookieManager;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
class CookieManagerCompatHoneyCombMr1 {
	
	public static void setAcceptFileSchemeCookies(boolean accept) {
		CookieManager.setAcceptFileSchemeCookies(accept);
	}
}
