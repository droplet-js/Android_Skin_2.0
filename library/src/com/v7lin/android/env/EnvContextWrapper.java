package com.v7lin.android.env;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;

/**
 * 皮肤插件 APK 就嫑签名，防止用户错误安装皮肤插件 APK ...
 * 
 * Window 层级的 View，救不了 ...
 * 
 * 非 inflater 的 View，救不了 ...
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvContextWrapper extends ContextWrapper {

	private final EnvResourcesManager mResourcesManager;

	private EnvSkinResourcesWrapper mResources;
	private Theme mTheme;

	public EnvContextWrapper(Context base, EnvResourcesManager manager) {
		super(base);
		mResourcesManager = manager;
		ensureEnvSkinResources();
	}

	private void ensureEnvSkinResources() {
		getResources();
	}

	public void setSystemResMap(SystemResMap resourcesMap) {
		ensureEnvSkinResources();
		mResources.setSystemResMap(resourcesMap);
	}

	public void setScheduleSkin(boolean scheduleSkin) {
		mResourcesManager.setScheduleSkin(scheduleSkin);
	}

	public void setScheduleFont(boolean scheduleFont) {
		mResourcesManager.setScheduleFont(scheduleFont);
	}

	@Override
	public Resources getResources() {
		if (mResources == null) {
			mResources = new EnvSkinResourcesWrapper(getBaseContext(), getBaseContext().getResources(), mResourcesManager);
		}
		return mResources;
	}

	@Override
	public Theme getTheme() {
		if (mTheme == null) {
			mTheme = getResources().newTheme();
			Resources.Theme theme = getBaseContext().getTheme();
			if (theme != null) {
				mTheme.setTo(theme);
			}
		}
		return mTheme;
	}
}
