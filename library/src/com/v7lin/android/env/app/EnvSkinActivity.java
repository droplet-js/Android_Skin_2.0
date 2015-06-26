package com.v7lin.android.env.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.v7lin.android.app.SuperActivity;
import com.v7lin.android.env.EnvCallback;
import com.v7lin.android.env.EnvContextWrapper;
import com.v7lin.android.env.EnvLayoutInflaterWrapper;
import com.v7lin.android.env.EnvResourcesManager;
import com.v7lin.android.env.LayoutInflaterWrapper;
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
	private View mContentView;

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

	/**
	 * Api 21
	 */
	@Override
	public Object getSystemService(String name) {
		if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
			if (mLayoutInflater == null) {
				mLayoutInflater = new EnvLayoutInflaterWrapper(LayoutInflater.from(getBaseContext()), this);
			}
			return mLayoutInflater;
		}
		return super.getSystemService(name);
	}

	@Override
	public void setContentView(int layoutResID) {
		// 支持 <merge/> 标签
		if (LayoutInflaterWrapper.startWithMergeTag(this, layoutResID)) {
			FrameLayout wrapper = new FrameLayout(this);
			View.inflate(this, layoutResID, wrapper);

			mContentView = wrapper;
			super.setContentView(wrapper);
		} else {
			View view = View.inflate(this, layoutResID, null);
			mContentView = view;
			super.setContentView(view);
		}
	}

	@Override
	public void setContentView(View view) {
		mContentView = view;
		super.setContentView(view);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		mContentView = view;
		super.setContentView(view, params);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mEnvUIChanger = new EnvActivityChanger();
		mEnvUIChanger.applyStyle(this, null, 0, 0, false);

		mSkinPath = EnvResourcesManager.getGlobal().getSkinPath(this);
		mFontPath = EnvResourcesManager.getGlobal().getFontPath(this);
	}

	public void scheduleSkin(String skinPath) {
		mSkinPath = skinPath;
		mEnvUIChanger.scheduleSkin(this, this);
		scheduleViewSkin(mContentView);
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

	public void scheduleFont(String fontPath) {
		mFontPath = fontPath;
		mEnvUIChanger.scheduleFont(this, this);
		scheduleViewFont(mContentView);
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
