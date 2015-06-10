package com.v7lin.android.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/**
 * 参考文献：
 * http://www.eoeandroid.com/forum.php?mod=viewthread&tid=272453&reltid=35453
 * &pre_thread_id=0&pre_pos=2&ext=CB
 * 
 * http://www.oschina.net/translate/40-developer-tips-for-android-optimization?
 * from=20130922
 * 
 * http://www.eoeandroid.com/thread-311194-1-1.html
 * 
 * https://github.com/Trinea/AndroidCommon
 * 
 * http://www.23code.com/
 * 
 * https://github.com/yangfuhai/afinal
 * 
 * https://github.com/wyouflf/xUtils
 * 
 * http://my.eoe.cn/1181897/archive/21230.html?f=nge
 * 
 * http://blog.csdn.net/cuiweijie3/article/details/9464925
 * 
 * https://github.com/davidleen/android-BaseAdapter-enhance
 * 
 * 混淆文献： http://blog.csdn.net/dianyueneo/article/details/7221323
 * 
 * 2.3 的新类 StrictMode 捕捉发生在应用程序主线程中耗时调用
 * 
 * 各种源码下载:http://grepcode.com/ http://rgruet.free.fr/public/
 * 
 * 手机测试平台:http://mc.sigma-rt.com/mc/doHomepageU.do
 * 
 * 基础 Activity ，方便对 Activity 进行管理
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class SuperActivity extends Activity {

	/**
	 * 修正某些手机（如红米Note）出现ActivityCloseExitAnimation失效
	 */
	private int activityCloseEnterAnimation;
	private int activityCloseExitAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TypedArray array = getTheme().obtainStyledAttributes(new int[] { android.R.attr.windowAnimationStyle });
		int windowAnimationStyleResId = array.getResourceId(0, 0);
		array.recycle();
		array = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] { android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation });
		activityCloseEnterAnimation = array.getResourceId(0, 0);
		activityCloseExitAnimation = array.getResourceId(1, 0);
		array.recycle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * 初始化最好在这里执行
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 一定是在 onStop 之前调用 可能是在 onPause 之前调用，也有可能在 onPause 之后调用
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 设置屏幕亮度
	 */
	public void setScreenBrightness(float screenBrightness) {
		WindowManager.LayoutParams attr = getWindow().getAttributes();
		attr.screenBrightness = screenBrightness;
		getWindow().setAttributes(attr);
	}

	/**
	 * 设置屏幕全屏与否
	 */
	public void setFullScreen(boolean fullScreen) {
		if (fullScreen) {// 全屏
			Window window = getWindow();
			if (window != null) {
				WindowManager.LayoutParams params = window.getAttributes();
				if (params != null) {
					params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
					window.setAttributes(params);
					window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
				}
			}
		} else {// 非全屏
			Window window = getWindow();
			if (window != null) {
				WindowManager.LayoutParams params = window.getAttributes();
				if (params != null) {
					params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
					window.setAttributes(params);
					window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
				}
			}
		}
	}

	/**
	 * 设置屏幕待机与否
	 */
	public void setKeepScreenOn(boolean keepScreenOn) {
		if (keepScreenOn) {// 不待机（常亮）
			Window window = getWindow();
			if (window != null) {
				window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		} else {// 待机（不常亮）
			Window window = getWindow();
			if (window != null) {
				window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
	}

	/**
	 * 设置屏幕旋转属性 {@link ActivityInfo#SCREEN_ORIENTATION_UNSPECIFIED etc.}
	 */
	@Override
	public void setRequestedOrientation(int requestedOrientation) {
		super.setRequestedOrientation(requestedOrientation);
	}
}
