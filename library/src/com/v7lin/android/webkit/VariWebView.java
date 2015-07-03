package com.v7lin.android.webkit;

import com.v7lin.android.env.webkit.CompatWebChromeClient;
import com.v7lin.android.env.webkit.CompatWebView;
import com.v7lin.android.env.webkit.CompatWebViewClient;
import com.v7lin.android.env.webkit.WebApiClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * WebView 的普通用法
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class VariWebView extends CompatWebView {

	private CompatWebViewClient mWebViewClient;
	private CompatWebChromeClient mWebChromeClient;
	private WebApiClient mDelegateWebApiClient;

	public VariWebView(Context context) {
		super(context);

		setup();
	}

	public VariWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setup();
	}

	public VariWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setup();
	}

	private void setup() {
		mWebViewClient = new CompatWebViewClient(getContext(), mWebApiClient);
		super.setWebViewClient(mWebViewClient);

		mWebChromeClient = new CompatWebChromeClient(mWebApiClient);
		super.setWebChromeClient(mWebChromeClient);
	}

	private WebApiClient mWebApiClient = new WebApiClient() {

		@Override
		public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
			super.doUpdateVisitedHistory(view, url, isReload);
			if (mDelegateWebApiClient != null) {
				mDelegateWebApiClient.doUpdateVisitedHistory(view, url, isReload);
			}
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return mDelegateWebApiClient != null ? mDelegateWebApiClient.shouldOverrideUrlLoading(view, url) : super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			if (mDelegateWebApiClient != null) {
				mDelegateWebApiClient.onProgressChanged(view, newProgress);
			}
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			if (mDelegateWebApiClient != null) {
				mDelegateWebApiClient.onReceivedTitle(view, title);
			}
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (mDelegateWebApiClient != null) {
				mDelegateWebApiClient.onPageStarted(view, url, favicon);
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (mDelegateWebApiClient != null) {
				mDelegateWebApiClient.onPageFinished(view, url);
			}
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			if (mDelegateWebApiClient != null) {
				mDelegateWebApiClient.onReceivedError(view, errorCode, description, failingUrl);
			}
		}
	};

	public void setWebApiClient(WebApiClient client) {
		this.mDelegateWebApiClient = client;
	}

	@Deprecated
	@Override
	public void setWebViewClient(WebViewClient client) {
		// super.setWebViewClient(client);
	}

	@Deprecated
	@Override
	public void setWebChromeClient(WebChromeClient client) {
		// super.setWebChromeClient(client);
	}
}
