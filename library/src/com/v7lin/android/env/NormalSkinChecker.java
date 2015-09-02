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
	
	private final boolean mCheckPkg;

	private NormalSkinChecker(boolean checkPkg) {
		super();
		mCheckPkg = checkPkg;
	}

	@Override
	public boolean isSkinValid(Context context, String skinPath) {
		boolean isValid = false;
		if (!TextUtils.isEmpty(skinPath)) {
			final File skinFile = new File(skinPath);
			if (skinFile.exists() && skinFile.isFile()) {
				PackageManager manager = context.getPackageManager();
				PackageInfo info = manager.getPackageArchiveInfo(skinFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
				if (mCheckPkg) {
					isValid = TextUtils.equals(context.getPackageName(), info.packageName);
				} else {
					isValid = true;
				}
			}
		}
		return isValid;
	}
	
	public static NormalSkinChecker newInstance(boolean checkPkg) {
		return new NormalSkinChecker(checkPkg);
	}
}
