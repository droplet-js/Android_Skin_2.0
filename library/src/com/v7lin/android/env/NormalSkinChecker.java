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
public class NormalSkinChecker implements SkinChecker {

	public NormalSkinChecker() {
		super();
	}

	@Override
	public boolean isSkinValid(Context context, String skinPath) {
		boolean isValid = false;
		if (!TextUtils.isEmpty(skinPath)) {
			final File skinFile = new File(skinPath);
			if (skinFile.exists() && skinFile.isFile()) {
				PackageManager manager = context.getPackageManager();
				PackageInfo info = manager.getPackageArchiveInfo(skinFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
				isValid = TextUtils.equals(context.getPackageName(), info.packageName);
			}
		}
		return isValid;
	}

	private static class NormalSkinCheckerHolder {
		private static final NormalSkinChecker INSTANCE = new NormalSkinChecker();
	}

	public static NormalSkinChecker getInstance() {
		return NormalSkinCheckerHolder.INSTANCE;
	}
}
