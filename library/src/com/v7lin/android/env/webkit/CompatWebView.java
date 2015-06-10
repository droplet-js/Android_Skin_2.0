package com.v7lin.android.env.webkit;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.DownloadListener;
import android.webkit.WebView;

/**
 * 防止 WebView 调用 destroy 后，继续调用 loadUrl 、loadData 或 loadDataWithBaseURL 方法，引发崩溃
 * 原因：destroy 后，WebView 的内核 WebViewCore 将被设置为空
 * 
 * WebView 的所有方法都必须在主线程中才能调用
 * 
 * Android WebView 依然遵循 dp、sp、px 等单位
 * 
 * WebView 内容获取
 * view.loadUrl(“javascript:window.handler.show(document.body.innerHTML);”);
 * 
 * 带图片的 html 不能用 loadData，应用 loadDataWithBaseURL
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class CompatWebView extends WebView {

	private final AtomicBoolean mIsAlive = new AtomicBoolean(true);
	private WebInitCompat mWebInitCompat;

	private DownloadListener mDelegateDownloadListener;
	
	public CompatWebView(Context context) {
		super(context);

		setup();
	}

	public CompatWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setup();
	}

	public CompatWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setup();
	}

	private void setup() {
		CookieManagerCompat.setAcceptCookie(getContext(), true);
		CookieManagerCompat.setAcceptFileSchemeCookies(true);
		
		mWebInitCompat = WebInitCompat.get(this);
		mWebInitCompat.setDefaultAttr();
		mWebInitCompat.setDefaultSetting();

		setDownloadListener(mDefaultDownloadListener);
	}

	@Override
	public void setDownloadListener(DownloadListener listener) {
		if (listener == mDefaultDownloadListener) {
			super.setDownloadListener(listener);
		} else {
			mDelegateDownloadListener = listener;
		}
	}

	private DownloadListener mDefaultDownloadListener = new DownloadListener() {

		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
			if (mDelegateDownloadListener != null) {
				mDelegateDownloadListener.onDownloadStart(url, userAgent, contentDisposition, mimetype, contentLength);
			} else {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				getContext().startActivity(intent);
			}
		}
	};

	@TargetApi(Build.VERSION_CODES.FROYO)
	@Override
	public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
		if (mIsAlive.get()) {
			super.loadUrl(url, additionalHttpHeaders);
		}
	}

	@Override
	public void loadUrl(String url) {
		if (mIsAlive.get()) {
			super.loadUrl(url);
		}
	}

	@Override
	public void postUrl(String url, byte[] postData) {
		if (mIsAlive.get()) {
			super.postUrl(url, postData);
		}
	}

	@Override
	public void loadData(String data, String mimeType, String encoding) {
		if (mIsAlive.get()) {
			super.loadData(data, mimeType, encoding);
		}
	}

	/**
	 * data 内容 -- %，会报找不到页面错误，页面全是乱码 -- #，会让你的goBack失效，但canGoBack是可以使用的 -- \ 和 ?
	 * 在转换时，会报错，因为它会把\当作转义符来使用，如果用两级转义，也不生效 --
	 * 遇到中文乱码问题，解决办法：参数传"utf-8"，页面的编码格式也必须是utf-8，这样编码统一就不会乱了
	 */
	@Override
	public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
		if (mIsAlive.get()) {
			super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
		}
	}

	/**
	 * 是否存在纵向滚动条
	 * 
	 * 当我们做类似上拉加载下一页这样的功能的时候，页面初始的时候需要知道当前WebView是否存在纵向滚动条
	 * 
	 * 如果有，则不加载下一页
	 * 
	 * 如果没有，则加载下一页直到其出现纵向滚动条
	 */
	public boolean existVerticalScrollbar() {
		return computeVerticalScrollRange() > computeVerticalScrollExtent();
	}

	/**
	 * 是否存在横向滚动条
	 */
	public boolean existHorizontalScrollbar() {
		return computeHorizontalScrollRange() > computeHorizontalScrollExtent();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ViewPager里非首屏WebView点击事件不响应
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY());
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onScrollChanged(int newX, int newY, int oldX, int oldY) {
		super.onScrollChanged(newX, newY, oldX, oldY);
		if (newY != oldY) {
			// 当前内容高度下从未触发过, 浏览器存在滚动条且滑动到将抵底部位置
			if (getContentHeight() * getScale() <= getHeight() + getScrollY()) {
			}
		}
	}

	@Override
	public void destroy() {
		mIsAlive.compareAndSet(true, false);
		super.destroy();
	}
}
