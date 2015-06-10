package com.v7lin.android.env.webkit;

/**
 * JavaScript 支持
 * 
 * JavaScript 回调方法是在 WebView 的专用线程，不支持主线程操作
 * 
 * 方便写混淆文件，而设置的一个接口
 * 
 * JavaScript 回调函数里传入参数，不能用双引号（""），要用单引号（''），不然在 2.x 机器上会失效
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public interface JSAccessor {
	
	public static final String JAVASCRIPT_FORMAT = "javascript:%1$s('%2$s')";
	
}
