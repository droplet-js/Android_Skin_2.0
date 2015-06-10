package com.v7lin.android.env;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.view.LayoutInflater;

/**
 * 皮肤插件 APK 就嫑签名，防止用户错误安装皮肤插件 APK ...
 * 
 * Window 层级的 View，救不了 ...
 * 
 * 非 inflater 的 View，救不了 ...
 * 
 * 特别的，要注意
 * 
 * <color name="white_delegate">@color/white</color> 取到的 resid为
 * white，不会是white_delegate
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvContextWrapper extends ContextWrapper {

	private final EnvResourcesManager mResourcesManager;

	private EnvSystemResourcesWrapper mResources;
	private Theme mTheme;
	private LayoutInflater mLayoutInflater;

	public EnvContextWrapper(Context base, EnvResourcesManager manager) {
		super(base);
		mResourcesManager = manager;
	}

	public void setSystemResMap(SystemResMap resourcesMap) {
		getResources();
		mResources.setSystemResMap(resourcesMap);
	}

	/**
	 * 尽量不用
	 */
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
}
