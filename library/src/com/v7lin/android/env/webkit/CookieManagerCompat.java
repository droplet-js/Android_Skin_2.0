package com.v7lin.android.env.webkit;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
class CookieManagerCompat {

	interface CookieManagerCompatImpl {
		public void setAcceptCookie(Context context, boolean accept);

		public void setAcceptFileSchemeCookies(boolean accept);
	}

	static class EarlyCookieManagerCompatImpl implements CookieManagerCompatImpl {

		@Override
		public void setAcceptCookie(Context context, boolean accept) {
			CookieSyncManager.getInstance();
			CookieManager.getInstance().setAcceptCookie(accept);
		}

		@Override
		public void setAcceptFileSchemeCookies(boolean accept) {

		}
	}

	static class HoneyCombMr1CookieManagerCompatImpl extends EarlyCookieManagerCompatImpl {

		@Override
		public void setAcceptFileSchemeCookies(boolean accept) {
			CookieManagerCompatHoneyCombMr1.setAcceptFileSchemeCookies(accept);
		}
	}

	static final CookieManagerCompatImpl IMPL;
	static {
		final int version = Build.VERSION.SDK_INT;
		if (version >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			IMPL = new HoneyCombMr1CookieManagerCompatImpl();
		} else {
			IMPL = new EarlyCookieManagerCompatImpl();
		}
	}

	public static void setAcceptCookie(Context context, boolean accept) {
		IMPL.setAcceptCookie(context, accept);
	}

	public static void setAcceptFileSchemeCookies(boolean accept) {
		IMPL.setAcceptFileSchemeCookies(accept);
	}
}
