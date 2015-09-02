package com.v7lin.android.env.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.v7lin.android.app.SuperActivity;
import com.v7lin.android.env.EnvCallback;
import com.v7lin.android.env.EnvContextWrapper;
import com.v7lin.android.env.EnvLayoutInflaterWrapper;
import com.v7lin.android.env.EnvResourcesManager;
import com.v7lin.android.env.NullViewMap;
import com.v7lin.android.env.SystemResMap;
import com.v7lin.android.env.widget.EnvActivityChanger;
import com.v7lin.android.env.widget.EnvUIChanger;
import com.v7lin.android.env.widget.XActivityCall;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvSkinActivity extends SuperActivity implements XActivityCall {

	private Context mAttachContext;
	private LayoutInflater mLayoutInflater;

	private EnvUIChanger<Activity, XActivityCall> mEnvUIChanger;

	private String mSkinPath;
	private String mFontPath;

	@Override
	protected void attachBaseContext(Context newBase) {
		mAttachContext = new EnvContextWrapper(newBase, EnvResourcesManager.getGlobal());
		super.attachBaseContext(mAttachContext);
	}

	/**
	 * 防止类似 Application 在 ActivityThread 接收广播时
	 * 
	 * 会取出 baseContext作一次强制类型转换，转换为 ContextImpl
	 */
	@Override
	public Context getBaseContext() {
		Context context = super.getBaseContext();
		if (context instanceof EnvContextWrapper) {
			context = ((EnvContextWrapper) context).getBaseContext();
		}
		return context;
	}

	public void setSystemResMap(SystemResMap resourcesMap) {
		if (mAttachContext instanceof EnvContextWrapper) {
			((EnvContextWrapper) mAttachContext).setSystemResMap(resourcesMap);
		}
	}

	@Override
	public Object getSystemService(String name) {
		if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
			if (mLayoutInflater == null) {
				mLayoutInflater = new EnvLayoutInflaterWrapper(LayoutInflater.from(getBaseContext()), this, NullViewMap.getInstance());
			}
			return mLayoutInflater;
		}
		return super.getSystemService(name);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mEnvUIChanger = new EnvActivityChanger();
		mEnvUIChanger.applyStyle(this, null, 0, 0, false, false);

		mSkinPath = EnvResourcesManager.getGlobal().getSkinPath(this);
		mFontPath = EnvResourcesManager.getGlobal().getFontPath(this);
	}

	/**
	 * 可继承 {@link EnvSkinActivity} 重写该函数
	 * 
	 * 设置视图资源，实现不支持换肤功能的视图，进行换肤
	 * 
	 * @param skinPath
	 */
	public void scheduleSkin(String skinPath) {
		mSkinPath = skinPath;
		mEnvUIChanger.scheduleSkin(this, this, false);
		scheduleViewSkin(findViewById(android.R.id.content));
	}

	private void scheduleViewSkin(View view) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleSkin();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupSkin((ViewGroup) view);
			}
		}
	}

	private void scheduleViewGroupSkin(ViewGroup group) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewSkin(child);
			}
		}
	}

	/**
	 * 可继承 {@link EnvSkinActivity} 重写该函数
	 * 
	 * 设置视图字体，实现不支持换字体功能的视图，进行换字体
	 * 
	 * @param skinPath
	 */
	public void scheduleFont(String fontPath) {
		mFontPath = fontPath;
		mEnvUIChanger.scheduleFont(this, this, false);
		scheduleViewFont(findViewById(android.R.id.content));
	}

	private void scheduleViewFont(View view) {
		if (view != null) {
			if (view instanceof EnvCallback) {
				((EnvCallback) view).scheduleFont();
			} else if (view instanceof ViewGroup) {
				scheduleViewGroupFont((ViewGroup) view);
			}
		}
	}

	private void scheduleViewGroupFont(ViewGroup group) {
		if (group != null) {
			final int childCount = group.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = group.getChildAt(i);
				scheduleViewFont(child);
			}
		}
	}

	protected boolean isSkinChanged() {
		return EnvResourcesManager.getGlobal().isSkinChanged(this, mSkinPath);
	}

	protected boolean isFontChanged() {
		return EnvResourcesManager.getGlobal().isFontChanged(this, mFontPath);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isSkinChanged()) {
			scheduleSkin(EnvResourcesManager.getGlobal().getSkinPath(this));
		}
		if (isFontChanged()) {
			scheduleFont(EnvResourcesManager.getGlobal().getFontPath(this));
		}
	}
}
