package com.v7lin.style.app;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.v7lin.android.env.SystemResMap;
import com.v7lin.android.env.app.EnvSkinActivity;
import com.v7lin.style.event.EventBus;
import com.v7lin.style.event.EventMode;
import com.v7lin.style.event.EventReceiver;
import com.v7lin.style.news.R;
import com.v7lin.style.widget.StyleToast;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class StyleActivity extends EnvSkinActivity implements SystemResMap {

	private static String sNavBarOverride;
	static {
		// Android allows a system property to override the presence of the
		// navigation bar.
		// Used by the emulator.
		// See
		// https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			try {
				Class<?> c = Class.forName("android.os.SystemProperties");
				Method m = c.getDeclaredMethod("get", String.class);
				m.setAccessible(true);
				sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
			} catch (Throwable e) {
				sNavBarOverride = null;
			}
		}
	}

	/**
	 * 滑动返回是否打开
	 */
	private static final boolean SIDE_BACK_STATE = true;

	/**
	 * 双击时间间隔
	 */
	private static final long DURATION_DOUBLE_EXIT = 2 * 1000;

	private static final String EVENT_STYLE = "com.v7lin.style.event.STYLE";
	private static final String ACTION_EXIT_APP = "com.v7lin.style.action.EXIT_APP";

	private boolean mHasNavigationBar = false;
	private boolean mStatusBarAvailable = false;
	private boolean mNavigationBarAvailable = false;

	private long mLastExitTime;

	private EventBus mEventBus;

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(newBase);
		checkDeviceEnv();
		checkPlatformEnv();
		checkDeveloperEnv();
		setSystemResMap(this);
	}

	/**
	 * 检测设备配置
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void checkDeviceEnv() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			// check device properties
			int showNavBarCFGResID = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
			if (showNavBarCFGResID > 0) {
				mHasNavigationBar = getResources().getBoolean(showNavBarCFGResID);
				if (TextUtils.equals(sNavBarOverride, "1")) {
					mHasNavigationBar = false;
				} else if (TextUtils.equals(sNavBarOverride, "0")) {
					mHasNavigationBar = true;
				}
			} else {
				mHasNavigationBar = !ViewConfiguration.get(this).hasPermanentMenuKey();
			}
		}
	}

	/**
	 * 检测Android_SDK配置（检测Activity的主题）
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void checkPlatformEnv() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			int[] attrs = { android.R.attr.windowTranslucentStatus, android.R.attr.windowTranslucentNavigation };
			TypedArray a = obtainStyledAttributes(attrs);
			mStatusBarAvailable = a.getBoolean(0, false);
			mNavigationBarAvailable = a.getBoolean(1, false);
		}
	}

	/**
	 * 检测开发者在代码中的设置
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void checkDeveloperEnv() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// check window flags
			WindowManager.LayoutParams params = getWindow().getAttributes();
			if ((params.flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0) {
				mStatusBarAvailable = true;
			}
			if ((params.flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION) != 0) {
				mNavigationBarAvailable = true;
			}
		}
	}

	@Override
	public int mapping(Context context, int resid, String resourcePackageName, String resourceTypeName, String resourceEntryName) {
		// 与 Android 4.4 以上 SystemBarTint 相关
		// 具体的参见 layout_top_bar.xml
		// 1.需要在 value-v19 上为Activity定义主题
		// 2.在视图顶部嵌入 com.v7lin.android.env.widget.CompatStatusBar，定义其 minHeight 即可
		if (resid == R.dimen.status_bar_height && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mStatusBarAvailable) {
			return context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		}
		if (resid == R.dimen.navigation_bar_height && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && mHasNavigationBar && mNavigationBarAvailable) {
			return context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
		}
		if (resid == R.dimen.navigation_bar_height_landscape && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && mHasNavigationBar && mNavigationBarAvailable) {
			return context.getResources().getIdentifier("navigation_bar_height_landscape", "dimen", "android");
		}
		if (resid == R.dimen.navigation_bar_width && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && mHasNavigationBar && mNavigationBarAvailable) {
			return context.getResources().getIdentifier("navigation_bar_width", "dimen", "android");
		}
		return 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mEventBus = new EventBus(this);
		mEventBus.onCreate();
		registerEventReceiver(true, EVENT_STYLE, EventMode.CREATE_DESTROY, mEventReceiver, ACTION_EXIT_APP);
	}

	private GestureDetector mDetector = new GestureDetector(this, new SimpleOnGestureListener() {

		private static final float MIN_VELOCITYX = 50;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e1 != null && e2 != null) {
				final float deltaX = e2.getRawX() - e1.getRawX();
				final float deltaY = e2.getRawY() - e1.getRawY();
				if (deltaX > Math.abs(deltaY) && deltaX > 200 && Math.abs(velocityX) > MIN_VELOCITYX && canFlingBack()) {
					// 右滑
					onBackPressed();
				}
				if (-deltaX > Math.abs(deltaY) && -deltaX > 200 && Math.abs(velocityX) > MIN_VELOCITYX) {
					// 左滑
				}
			}
			return super.onFling(e1, e2, velocityX, velocityY);
		}
	});

	public boolean canFlingBack() {
		return SIDE_BACK_STATE;
	}

	private EventReceiver mEventReceiver = new EventReceiver() {

		@Override
		public void onEvent(String action, Bundle extras) {
			if (TextUtils.equals(action, ACTION_EXIT_APP)) {
				finish();
			}
		}
	};

	public final void registerEventReceiver(boolean isLocal, String eventKey, EventMode mode, EventReceiver receiver, String... actions) {
		if (mEventBus != null) {
			mEventBus.registerEventReceiver(isLocal, eventKey, mode, receiver, actions);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (mEventBus != null) {
			mEventBus.onStart();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mEventBus != null) {
			mEventBus.onResume();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mDetector.onTouchEvent(event);
		// return super.onTouchEvent(event);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mEventBus != null) {
			mEventBus.onPause();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mEventBus != null) {
			mEventBus.onStop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mEventBus != null) {
			mEventBus.onDestroy();
		}
	}

	@Override
	public void finish() {
		super.finish();
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
		// getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
		if (TextUtils.equals(getIntent().getAction(), Intent.ACTION_MAIN)) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if ((System.currentTimeMillis() - mLastExitTime) > DURATION_DOUBLE_EXIT) {
					exitTips();
					mLastExitTime = System.currentTimeMillis();
					return true;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		hideKeyboard();
		if (TextUtils.equals(getIntent().getAction(), Intent.ACTION_MAIN)) {
			exitApp();
		}
		super.onBackPressed();
	}

	private void hideKeyboard() {
		IBinder windowToken = getWindow().getDecorView().getWindowToken();
		if (windowToken != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	private void exitTips() {
		try {
			ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(), 0);
			final String formatArgs = String.valueOf(info.loadLabel(getPackageManager()));
			final String text = getResources().getString(R.string.exit_tips_format, formatArgs);
			StyleToast.makeText(this, text, StyleToast.LENGTH_SHORT).show();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	private void exitApp() {
		EventBus.sendEvent(this, ACTION_EXIT_APP, null, true);
	}
}
