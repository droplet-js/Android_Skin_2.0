package com.v7lin.android.env;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class VerSkinChecker implements SkinChecker {

	private final int ver;

	private VerSkinChecker(int ver) {
		super();
		this.ver = ver;
	}

	@Override
	public boolean isSkinValid(Context context, String skinPath) {
		boolean isValid = false;
		if (!TextUtils.isEmpty(skinPath)) {
			final File skinFile = new File(skinPath);
			if (skinFile.exists() && skinFile.isFile()) {
				PackageManager manager = context.getPackageManager();
				PackageInfo info = manager.getPackageArchiveInfo(skinFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
				isValid = TextUtils.equals(context.getPackageName(), info.packageName) && info.versionCode == ver;
			}
		}
		return isValid;
	}

	public static VerSkinChecker newInstance(int ver) {
		return new VerSkinChecker(ver);
	}
}
