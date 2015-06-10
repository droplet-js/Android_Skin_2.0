package com.v7lin.android.env.webkit;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class WebSettingsCompat {
	
	public enum PluginStateCompat {
        ON,
        ON_DEMAND,
        OFF
    }
	
	public enum ZoomDensityCompat {
        FAR,
        MEDIUM,
        CLOSE;
    }

	interface WebSettingsCompatImpl {
		/**
		 * JavaScript 支持
		 */
		public void setJavaScriptEnabled(WebSettings settings, boolean flag);// early

		public void setJavaScriptCanOpenWindowsAutomatically(WebSettings settings, boolean flag);// early
		
		/**
		 * Web 图片
		 */
		public void setLoadsImagesAutomatically(WebSettings settings, boolean flag);// early
		
		public boolean getLoadsImagesAutomatically(WebSettings settings);// early

		public void setBlockNetworkImage(WebSettings settings, boolean flag);// early
		
		public boolean getBlockNetworkImage(WebSettings settings);// early
		
		/**
		 * encoding
		 */
		public void setDefaultTextEncodingName(WebSettings settings, String encoding);// early

		/**
		 * WebView 去除缩放功能但隐藏缩放控件
		 */
		public void setDefaultZoom(WebSettings settings, ZoomDensityCompat zoom);// early

		public void setSupportZoom(WebSettings settings, boolean enabled);// early

		public void setBuiltInZoomControls(WebSettings settings, boolean enabled);// early

		public void setDisplayZoomControls(WebSettings settings, boolean enabled);

		/**
		 * 自适应屏幕
		 */
		public void setUseWideViewPort(WebSettings settings, boolean use);// early

		/**
		 * 加载模式
		 */
		public void setLoadWithOverviewMode(WebSettings settings, boolean overview);

		/**
		 * 保持表单和密码
		 */
		public void setSaveFormData(WebSettings settings, boolean save);// early

		public void setSavePassword(WebSettings settings, boolean save);// early

		/**
		 * 线程优先级调整
		 */
		public void setRenderPriority(WebSettings settings, RenderPriority priority);// early

		/**
		 * 用WebView显示图片，可使用这个参数
		 */
		public void setLayoutAlgorithm(WebSettings settings, LayoutAlgorithm l);// early
		
		public void setSupportMultipleWindows(WebSettings settings, boolean support);

		/**
		 * @see WebSettings#LOAD_CACHE_ONLY 不使用网络，只读取本地缓存数据
		 * 
		 * @see LOAD_DEFAULT 根据cache-control决定是否从网络上取数据
		 * 
		 * @see LOAD_CACHE_NORMAL API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
		 * 
		 * @see LOAD_NO_CACHE 不使用缓存，只从网络获取数据
		 * 
		 * @see LOAD_CACHE_ELSE_NETWORK 只要本地有，无论是否过期、或者no-cache，都使用缓存中的数据
		 * 
		 * www.taobao.com的cache-control为no-cache，在模式LOAD_DEFAULT下，无论如何都会从网络上取数据，如果没有网络，就会出现错误页面
		 * 在LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取
		 */
		public void setCacheMode(WebSettings settings, int mode);// early

		/**
		 * 开启 DOM storage API 功能
		 */
		public void setDomStorageEnabled(WebSettings settings, boolean flag);

		/**
		 * 开启 database storage API 功能
		 */
		public void setDatabaseEnabled(WebSettings settings, boolean flag);// early

		/**
		 * 开启 Application Caches 功能
		 */
		public void setAppCacheEnabled(WebSettings settings, boolean flag);

		/**
		 * 设置最大缓存值 8M
		 */
		public void setAppCacheMaxSize(WebSettings settings, long appCacheMaxSize);
		
		/**
		 * 设置缓存路径
		 */
		public void setAppCachePath(WebSettings settings, String appCachePath);

		/**
		 * 插件
		 */
		public void setPluginState(WebSettings settings, PluginStateCompat state);

		/**
		 * 可以读取内容缓存
		 */
		public void setAllowContentAccess(WebSettings settings, boolean allow);

		/**
		 * 可以读取文件缓存(manifest生效)
		 */
		public void setAllowFileAccess(WebSettings settings, boolean allow);// early

		public void setAllowFileAccessFromFileURLs(WebSettings settings, boolean flag);

		public void setAllowUniversalAccessFromFileURLs(WebSettings settings, boolean flag);

		public String getUserAgentString(WebSettings settings);

		public void setUserAgentString(WebSettings settings, String ua);
	}

	static class EarlyWebSettingsCompatImpl implements WebSettingsCompatImpl {

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void setJavaScriptEnabled(WebSettings settings, boolean flag) {
			settings.setJavaScriptEnabled(flag);
		}

		@Override
		public void setJavaScriptCanOpenWindowsAutomatically(WebSettings settings, boolean flag) {
			settings.setJavaScriptCanOpenWindowsAutomatically(flag);
		}

		@Override
		public void setLoadsImagesAutomatically(WebSettings settings, boolean flag) {
			settings.setLoadsImagesAutomatically(flag);
		}

		@Override
		public boolean getLoadsImagesAutomatically(WebSettings settings) {
			return settings.getLoadsImagesAutomatically();
		}

		@Override
		public void setBlockNetworkImage(WebSettings settings, boolean flag) {
			settings.setBlockNetworkImage(flag);
		}

		@Override
		public boolean getBlockNetworkImage(WebSettings settings) {
			return settings.getBlockNetworkImage();
		}

		@Override
		public void setDefaultTextEncodingName(WebSettings settings, String encoding) {
			settings.setDefaultTextEncodingName(encoding);
		}

		@Override
		public void setDefaultZoom(WebSettings settings, ZoomDensityCompat zoom) {

		}

		@Override
		public void setSupportZoom(WebSettings settings, boolean enabled) {
			settings.setSupportZoom(enabled);
		}

		@Override
		public void setBuiltInZoomControls(WebSettings settings, boolean enabled) {
			settings.setBuiltInZoomControls(enabled);
		}

		@Override
		public void setDisplayZoomControls(WebSettings settings, boolean enabled) {

		}

		@Override
		public void setUseWideViewPort(WebSettings settings, boolean use) {
			settings.setUseWideViewPort(use);
		}

		@Override
		public void setLoadWithOverviewMode(WebSettings settings, boolean overview) {

		}

		@Override
		public void setSaveFormData(WebSettings settings, boolean save) {
			settings.setSaveFormData(save);
		}

		@Override
		public void setSavePassword(WebSettings settings, boolean save) {
			settings.setSavePassword(save);
		}

		@Override
		public void setRenderPriority(WebSettings settings, RenderPriority priority) {
			settings.setRenderPriority(priority);
		}

		@Override
		public void setLayoutAlgorithm(WebSettings settings, LayoutAlgorithm l) {
			settings.setLayoutAlgorithm(l);
		}

		@Override
		public void setSupportMultipleWindows(WebSettings settings, boolean support) {
			settings.setSupportMultipleWindows(support);
		}

		@Override
		public void setCacheMode(WebSettings settings, int mode) {
			settings.setCacheMode(mode);
		}

		@Override
		public void setDomStorageEnabled(WebSettings settings, boolean flag) {

		}

		@Override
		public void setDatabaseEnabled(WebSettings settings, boolean flag) {
			settings.setDatabaseEnabled(flag);
		}

		@Override
		public void setAppCacheEnabled(WebSettings settings, boolean flag) {

		}

		@Override
		public void setAppCacheMaxSize(WebSettings settings, long appCacheMaxSize) {

		}

		@Override
		public void setAppCachePath(WebSettings settings, String appCachePath) {
			
		}

		@Override
		public void setPluginState(WebSettings settings, PluginStateCompat state) {

		}

		@Override
		public void setAllowContentAccess(WebSettings settings, boolean allow) {

		}

		@Override
		public void setAllowFileAccess(WebSettings settings, boolean allow) {
			settings.setAllowFileAccess(allow);
		}

		@Override
		public void setAllowFileAccessFromFileURLs(WebSettings settings, boolean flag) {

		}

		@Override
		public void setAllowUniversalAccessFromFileURLs(WebSettings settings, boolean flag) {

		}

		@Override
		public String getUserAgentString(WebSettings settings) {
			return settings.getUserAgentString();
		}

		@Override
		public void setUserAgentString(WebSettings settings, String ua) {
			settings.setUserAgentString(ua);
		}
	}

	// 2.1
	static class EclairMr1WebSettingsCompatImpl extends EarlyWebSettingsCompatImpl {
		
		@Override
		public void setDefaultZoom(WebSettings settings, ZoomDensityCompat zoom) {
			WebSettingsCompatEclairMr1.setDefaultZoom(settings, zoom);
		}

		@Override
		public void setLoadWithOverviewMode(WebSettings settings, boolean overview) {
			WebSettingsCompatEclairMr1.setLoadWithOverviewMode(settings, overview);
		}

		@Override
		public void setDomStorageEnabled(WebSettings settings, boolean flag) {
			WebSettingsCompatEclairMr1.setDomStorageEnabled(settings, flag);
		}

		@Override
		public void setAppCacheEnabled(WebSettings settings, boolean flag) {
			WebSettingsCompatEclairMr1.setAppCacheEnabled(settings, flag);
		}

		@Override
		public void setAppCacheMaxSize(WebSettings settings, long appCacheMaxSize) {
			WebSettingsCompatEclairMr1.setAppCacheMaxSize(settings, appCacheMaxSize);
		}
		
		@Override
		public void setAppCachePath(WebSettings settings, String appCachePath) {
			WebSettingsCompatEclairMr1.setAppCachePath(settings, appCachePath);
		}
	}
	
	// 2.2
	static class FroyoWebSettingsCompatImpl extends EclairMr1WebSettingsCompatImpl {

		@Override
		public void setPluginState(WebSettings settings, PluginStateCompat state) {
			WebSettingsCompatFroyo.setPluginState(settings, state);
		}
	}

	// 3.0
	static class HoneyCombWebSettingsCompatImpl extends FroyoWebSettingsCompatImpl {

		@Override
		public void setDisplayZoomControls(WebSettings settings, boolean enabled) {
			WebSettingsCompatHoneyComb.setDisplayZoomControls(settings, enabled);
		}

		@Override
		public void setAllowContentAccess(WebSettings settings, boolean allow) {
			WebSettingsCompatHoneyComb.setAllowContentAccess(settings, allow);
		}
	}

	// 4.1
	static class JellyBeanWebSettingsCompatImpl extends HoneyCombWebSettingsCompatImpl {

		@Override
		public void setAllowFileAccessFromFileURLs(WebSettings settings, boolean flag) {
			WebSettingsCompatJellyBean.setAllowFileAccessFromFileURLs(settings, flag);
		}

		@Override
		public void setAllowUniversalAccessFromFileURLs(WebSettings settings, boolean flag) {
			WebSettingsCompatJellyBean.setAllowUniversalAccessFromFileURLs(settings, flag);
		}
	}

	public static WebSettingsCompat get(WebView view) {
		return new WebSettingsCompat(view);
	}

	private final WebSettings settings;
	private final WebSettingsCompatImpl impl;

	private WebSettingsCompat(WebView view) {
		this(Build.VERSION.SDK_INT, view);
	}

	private WebSettingsCompat(int apiVersion, WebView view) {
		super();
		if (apiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
			impl = new JellyBeanWebSettingsCompatImpl();
		} else if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
			impl = new HoneyCombWebSettingsCompatImpl();
		} else if (apiVersion >= Build.VERSION_CODES.FROYO) {
			impl = new FroyoWebSettingsCompatImpl();
		} else if (apiVersion >= Build.VERSION_CODES.ECLAIR_MR1) {
			impl = new EclairMr1WebSettingsCompatImpl();
		} else {
			impl = new EarlyWebSettingsCompatImpl();
		}
		settings = view.getSettings();
	}

	public void setJavaScriptEnabled(boolean flag) {
		impl.setJavaScriptEnabled(settings, flag);
	}

	public void setJavaScriptCanOpenWindowsAutomatically(boolean flag) {
		impl.setJavaScriptCanOpenWindowsAutomatically(settings, flag);
	}

	public void setLoadsImagesAutomatically(boolean flag) {
		impl.setLoadsImagesAutomatically(settings, flag);
	}

	public boolean getLoadsImagesAutomatically() {
		return impl.getLoadsImagesAutomatically(settings);
	}

	public void setBlockNetworkImage(boolean flag) {
		impl.setBlockNetworkImage(settings, flag);
	}
	
	public boolean getBlockNetworkImage() {
		return impl.getBlockNetworkImage(settings);
	}

	public void setDefaultTextEncodingName(String encoding) {
		impl.setDefaultTextEncodingName(settings, encoding);
	}

	public void setDefaultZoom(ZoomDensityCompat zoom) {
		impl.setDefaultZoom(settings, zoom);
	}

	public void setSupportZoom(boolean enabled) {
		impl.setSupportZoom(settings, enabled);
	}

	public void setBuiltInZoomControls(boolean enabled) {
		impl.setBuiltInZoomControls(settings, enabled);
	}

	public void setDisplayZoomControls(boolean enabled) {
		impl.setDisplayZoomControls(settings, enabled);
	}

	public void setUseWideViewPort(boolean use) {
		impl.setUseWideViewPort(settings, use);
	}

	public void setLoadWithOverviewMode(boolean overview) {
		impl.setLoadWithOverviewMode(settings, overview);
	}

	public void setSaveFormData(boolean save) {
		impl.setSaveFormData(settings, save);
	}

	public void setSavePassword(boolean save) {
		impl.setSavePassword(settings, save);
	}

	public void setRenderPriority(RenderPriority priority) {
		impl.setRenderPriority(settings, priority);
	}

	public void setLayoutAlgorithm(LayoutAlgorithm l) {
		impl.setLayoutAlgorithm(settings, l);
	}

	public void setSupportMultipleWindows(boolean support) {
		impl.setSupportMultipleWindows(settings, support);
	}

	public void setCacheMode(int mode) {
		impl.setCacheMode(settings, mode);
	}

	public void setDomStorageEnabled(boolean flag) {
		impl.setDomStorageEnabled(settings, flag);
	}

	public void setDatabaseEnabled(boolean flag) {
		impl.setDatabaseEnabled(settings, flag);
	}

	public void setAppCacheEnabled(boolean flag) {
		impl.setAppCacheEnabled(settings, flag);
	}

	public void setAppCacheMaxSize(long appCacheMaxSize) {
		impl.setAppCacheMaxSize(settings, appCacheMaxSize);
	}

	public void setAppCachePath(String appCachePath) {
		impl.setAppCachePath(settings, appCachePath);
	}

	public void setPluginState(PluginStateCompat state) {
		impl.setPluginState(settings, state);
	}

	public void setAllowContentAccess(boolean allow) {
		impl.setAllowContentAccess(settings, allow);
	}

	public void setAllowFileAccess(boolean allow) {
		impl.setAllowFileAccess(settings, allow);
	}

	public void setAllowFileAccessFromFileURLs(boolean flag) {
		impl.setAllowFileAccessFromFileURLs(settings, flag);
	}

	public void setAllowUniversalAccessFromFileURLs(boolean flag) {
		impl.setAllowUniversalAccessFromFileURLs(settings, flag);
	}

	public String getUserAgentString() {
		return impl.getUserAgentString(settings);
	}

	public void setUserAgentString(String ua) {
		impl.setUserAgentString(settings, ua);
	}
}
