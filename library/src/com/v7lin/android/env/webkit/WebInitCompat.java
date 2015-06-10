package com.v7lin.android.env.webkit;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

import com.v7lin.android.env.webkit.WebSettingsCompat.PluginStateCompat;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressLint("InlinedApi")
class WebInitCompat {

	public static final int LAYER_TYPE_NONE = WebInitCompat.isUseSupport() ? 0 : View.LAYER_TYPE_NONE;
	public static final int LAYER_TYPE_SOFTWARE = WebInitCompat.isUseSupport() ? 1 : View.LAYER_TYPE_SOFTWARE;
	public static final int LAYER_TYPE_HARDWARE = WebInitCompat.isUseSupport() ? 2 : View.LAYER_TYPE_HARDWARE;
	
	private static boolean isUseSupport() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
	}

	interface WebApiCompatImpl {
		public void setDefaultAttr(WebView view);

		public void setDefaultSetting(WebView view);
	}

	static class EarlyWebApiCompatImpl implements WebApiCompatImpl {

		@Override
		public void setDefaultAttr(WebView view) {
			// 去除滚动条白色背景，必须在代码里面添加才有效
			view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
			view.setScrollbarFadingEnabled(true);
			view.setDrawingCacheEnabled(true);
			view.setLongClickable(true);
			view.setBackgroundResource(android.R.color.transparent);
			view.setBackgroundColor(Color.TRANSPARENT);
			view.getBackground().setAlpha(0);
			view.setFocusable(true);
			view.setFocusableInTouchMode(true);
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void setDefaultSetting(WebView view) {
			WebSettingsCompat settings = WebSettingsCompat.get(view);

			// JavaScript 支持
			settings.setJavaScriptEnabled(true);
			settings.setJavaScriptCanOpenWindowsAutomatically(true); // 允许 JavaScript 弹出窗口
			
			// web 图片
			settings.setLoadsImagesAutomatically(true);

			// WebView 去除缩放功能但隐藏缩放控件
			settings.setSupportZoom(false);
			settings.setBuiltInZoomControls(false);
			settings.setDisplayZoomControls(false);

			// 自适应屏幕
			settings.setUseWideViewPort(true);

			// 加载模式
			settings.setLoadWithOverviewMode(true);

			// 保持表单和密码
			settings.setSaveFormData(false);
			settings.setSavePassword(false);

			// 线程优先级调整
			settings.setRenderPriority(RenderPriority.HIGH);

			// 用WebView显示图片，可使用这个参数
			settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
			
			settings.setSupportMultipleWindows(true);

			// LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
			// LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据
			// LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level
			// 11开始作用同LOAD_DEFAULT模式
			// LOAD_NO_CACHE: 不使用缓存，只从网络获取数据
			// LOAD_CACHE_ELSE_NETWORK: 只要本地有，无论是否过期、或者no-cache，都使用缓存中的数据

			// www.taobao.com的cache-control为no-cache，在模式LOAD_DEFAULT下，无论如何都会从网络上取数据，如果没有网络，就会出现错误页面；
			// 在LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取
			settings.setCacheMode(WebSettings.LOAD_DEFAULT);
			// 开启 DOM storage API 功能
			settings.setDomStorageEnabled(true);
			// 开启 database storage API 功能
			settings.setDatabaseEnabled(true);
			// 开启 Application Caches 功能
			settings.setAppCacheEnabled(true);
			// 设置最大缓存值 8M
			settings.setAppCacheMaxSize(8 * 1024 * 1024);

			settings.setPluginState(PluginStateCompat.ON_DEMAND);

			// 可以读取内容缓存
			settings.setAllowContentAccess(true);
			// 可以读取文件缓存(manifest生效)
			settings.setAllowFileAccess(true);
			settings.setAllowFileAccessFromFileURLs(true);
			settings.setAllowUniversalAccessFromFileURLs(true);
		}
	}

	static class HoneyCombWebApiCompatImpl extends EarlyWebApiCompatImpl {

		@Override
		public void setDefaultAttr(WebView view) {
			super.setDefaultAttr(view);
			// 防止加载图片白屏，为了透明
//			WebInitCompatHoneyComb.setLayerType(view, LAYER_TYPE_SOFTWARE, null);
		}
	}

	public static WebInitCompat get(WebView view) {
		return new WebInitCompat(view);
	}

	private final WebView view;
	private final WebApiCompatImpl impl;

	private WebInitCompat(WebView view) {
		this(Build.VERSION.SDK_INT, view);
	}

	private WebInitCompat(int apiVersion, WebView view) {
		super();
		if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
			impl = new HoneyCombWebApiCompatImpl();
		} else {
			impl = new EarlyWebApiCompatImpl();
		}
		this.view = view;
	}

	public void setDefaultAttr() {
		impl.setDefaultAttr(view);
	}

	public void setDefaultSetting() {
		impl.setDefaultSetting(view);
	}
}
