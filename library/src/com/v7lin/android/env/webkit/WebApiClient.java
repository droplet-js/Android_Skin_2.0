package com.v7lin.android.env.webkit;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class WebApiClient {

	public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {

	}

	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return false;
	}

	public void onProgressChanged(WebView view, int newProgress) {

	}

	public void onReceivedTitle(WebView view, String title) {

	}

	public void onPageStarted(WebView view, String url, Bitmap favicon) {

	}

	public void onPageFinished(WebView view, String url) {

	}

	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

	}
}
