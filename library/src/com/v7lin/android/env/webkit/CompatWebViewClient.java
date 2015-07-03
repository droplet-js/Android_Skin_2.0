package com.v7lin.android.env.webkit;

import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 辅助处理各类通知、请求等事件
 * 
 * 错误页面时，调用系统错误页面时：
 * 
 * 有的机器（Sony LT28h,4.0.4）会回调 onReceivedError 之后，又立即回调 onPageStarted，执行两次 onPageStarted 和两次 onPageFinished
 * 
 * 有的机器（三星 S5830,2.3.5）第一次错误页面执行一次 onPageStarted 和两次 onPageFinished，随后是一次 onPageStarted 和一次 onPageFinished
 * 
 * 有的机器（三星 I9500,4.4.2）则不会
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatWebViewClient extends WebViewClient {
	
	private static final String JAVASCRIPT_404 = "javascript:" +
			//
			//"document.body.style.color='#000000';" +
			//
			"document.body.innerHTML=\"\"";

	private WebApiClient mWebApiClient;
	private ClientManager mClientManager;

	public CompatWebViewClient(Context context, WebApiClient client) {
		super();
		this.mWebApiClient = client;

		setup();
	}

	private void setup() {
		mClientManager = new ClientManager();
	}

	/**
	 * 控制更新返回键
	 */
	@Override
	public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
		super.doUpdateVisitedHistory(view, url, isReload);
		if (mWebApiClient != null) {
			mWebApiClient.doUpdateVisitedHistory(view, url, isReload);
		}
	}

	/**
	 * 控制加载新 URL
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		boolean shouldOverride = mWebApiClient != null && mWebApiClient.shouldOverrideUrlLoading(view, url);
		if (!shouldOverride) {
			shouldOverride = true;
			view.loadUrl(url);
		}
		return shouldOverride || super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		mClientManager.markStarted(mWebApiClient, view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		mClientManager.markFinished(mWebApiClient, view, url);
	}

	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
		mClientManager.markError(mWebApiClient, view, errorCode, description, failingUrl);
	}

	static class ClientManager {

		private final Js404Manager mJs404Manager = new Js404Manager();

		private boolean mIsErrorCall = false;
		private boolean mIsRightFinish = true;

		public ClientManager() {
			super();
		}

		public void markError(WebApiClient client, WebView view, int errorCode, String description, String failingUrl) {
			mIsErrorCall = true;
			if (client != null) {
				client.onReceivedError(view, errorCode, description, failingUrl);
			}
			mJs404Manager.markError(view);
		}

		public void markStarted(WebApiClient client, WebView view, String url, Bitmap favicon) {
			if (!mIsErrorCall) {
				mJs404Manager.reset();
				if (client != null) {
					client.onPageStarted(view, url, favicon);
				}
			} else {
				mIsRightFinish = false;
			}
		}

		public void markFinished(WebApiClient client, WebView view, String url) {
			if (mIsRightFinish) {
				if (client != null) {
					client.onPageFinished(view, url);
				}
			}
			mJs404Manager.markFinished(view);
			mIsErrorCall = false;
			mIsRightFinish = true;
		}
	}

	static class Js404Manager {

		private final AtomicBoolean markError = new AtomicBoolean(false);

		public Js404Manager() {
			super();
		}

		public void reset() {
			markError.set(false);
		}

		public void markError(WebView view) {
			markError.compareAndSet(false, true);
			new Js404Action(view).exec();// 避免系统错误页面闪烁
		}

		public void markFinished(WebView view) {
			if (markError.compareAndSet(true, false)) {
				new Js404Action(view).exec();// 避免JS错误页面被取缔
			}
		}
	}

	static class Js404Action implements Runnable {

		private WebView view;

		public Js404Action(WebView view) {
			super();
			this.view = view;
		}

		public void exec() {
			view.post(this);
		}

		@Override
		public void run() {
			// 用javascript隐藏系统定义的404页面信息
			view.loadUrl(JAVASCRIPT_404);
		}
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
		// super.onReceivedSslError(view, handler, error);
		// 忽略证书的错误继续Load页面内容
		handler.proceed();// 默认 handler.cancel();
	}
}
