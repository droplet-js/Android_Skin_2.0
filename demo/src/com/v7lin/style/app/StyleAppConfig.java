package com.v7lin.style.app;

import com.v7lin.android.app.SuperAppConfig;
import com.v7lin.android.env.EnvResourcesManager;
import com.v7lin.android.env.SharedPrefSetup;
import com.v7lin.android.env.VerSkinChecker;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class StyleAppConfig extends SuperAppConfig {

	@Override
	public void onCreate() {
		super.onCreate();

		EnvResourcesManager.getGlobal().setEnvSetup(SharedPrefSetup.getGlobal());
		EnvResourcesManager.getGlobal().setSkinChecker(VerSkinChecker.newInstance(1000));
		
		// 校验当前设置的皮肤是否被删除
	}
}
