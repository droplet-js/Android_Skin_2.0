package com.v7lin.android.env;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class SharedPrefSetup implements EnvSetup {
	
	private static final String ENV_SETUP = "env_setup";

	private static final String KEY_SKIN_PATH = "skin_path";
	private static final String KEY_FONT_PATH = "font_path";
	
	private final String mConfig;

	public SharedPrefSetup(String cfg) {
		super();
		mConfig = cfg;
	}

	@Override
	public String getSkinPath(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
		return preferences.getString(KEY_SKIN_PATH, "");
	}

	public void setSkinPath(Context context, String skinPath) {
		SharedPreferences preferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
		preferences.edit().putString(KEY_SKIN_PATH, skinPath).commit();
	}

	@Override
	public String getFontPath(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
		return preferences.getString(KEY_FONT_PATH, "");
	}

	public void setFontPath(Context context, String fontPath) {
		SharedPreferences preferences = context.getSharedPreferences(mConfig, Context.MODE_PRIVATE);
		preferences.edit().putString(KEY_FONT_PATH, fontPath).commit();
	}

	private static class SharedPrefSetupHolder {
		private static final SharedPrefSetup INSTANCE = new SharedPrefSetup(ENV_SETUP);
	}

	public static SharedPrefSetup getGlobal() {
		return SharedPrefSetupHolder.INSTANCE;
	}
}
