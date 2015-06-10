package com.v7lin.android.env.webkit;

import android.webkit.WebChromeClient;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;

/**
 * 辅助处理 JavaScript 的对话框、网站图标、网站标题等
 * 
 * 亲，别指望 onProgressChanged 方法了，就让它单纯去控制进度条吧
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatWebChromeClient extends WebChromeClient {

	private WebApiClient mWebApiClient;

	public CompatWebChromeClient(WebApiClient client) {
		super();
		this.mWebApiClient = client;
	}
	
	@Override
	public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, QuotaUpdater quotaUpdater) {
		// super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
		// 扩充数据库的容量（在WebChromeClinet中实现）
		quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
		if (mWebApiClient != null) {
			mWebApiClient.onProgressChanged(view, newProgress);
		}
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
		if (mWebApiClient != null) {
			mWebApiClient.onReceivedTitle(view, title);
		}
	}
}
