package com.v7lin.android.os.env;

import java.io.File;

import android.content.Context;
import android.os.Build;
import android.os.FileUtils;
import android.text.TextUtils;

import com.v7lin.android.os.storage.StorageUtils;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public final class PathUtils implements PathConst {

	private static String sRtlPath = PATH_APP_DIR;

	private PathUtils() {
		super();
	}

	public static void setAppRtlPath(String rtlPath) {
		sRtlPath = rtlPath;
	}

	private static String getAppRtlPath() {
		return !TextUtils.isEmpty(sRtlPath) ? sRtlPath : PATH_APP_DIR;
	}

	private static File makeDir(Context context, boolean isPrivate) {
		File storageDir = StorageUtils.getStorageDir(context, isPrivate);
		File appDir = new File(storageDir, PathUtils.getAppRtlPath());
		if (!appDir.exists()) {
			appDir.mkdirs();
		}
		return appDir;
	}

	private static File makeSubDir(Context context, String subPath, boolean isPrivate) {
		File appDir = PathUtils.makeDir(context, isPrivate);
		File subDir = new File(appDir, subPath);
		if (!subDir.exists()) {
			subDir.mkdirs();
		}
		return subDir;
	}
	
	/**
	 * 设置文件权限
	 */
	public static void setFilePermissionsFromMode(String name, int mode, int extraPermissions) {
		int perms = FileUtils.S_IRUSR | FileUtils.S_IWUSR | FileUtils.S_IRGRP | FileUtils.S_IWGRP | extraPermissions;
		if ((mode & Context.MODE_WORLD_READABLE) != 0) {
			perms |= FileUtils.S_IROTH;
		}
		if ((mode & Context.MODE_WORLD_WRITEABLE) != 0) {
			perms |= FileUtils.S_IWOTH;
		}
		FileUtils.setPermissions(name, perms, -1, -1);
	}

	/**
	 * 为了测试方便，暂时先放在SD卡上
	 * 
	 * 这个需要保护起来，避免用户误删
	 */
	public static File getSkinDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_SKIN, false);
	}

	public static File getFontDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_FONT, false);
	}

	public static File getCrashDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_CRASH, false);
	}

	public static File getCacheDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_CACHE, false);
	}

	public static File getDataBasesDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_DATABASES, false);
	}

	public static File getSharedPrefsDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_SHARED_PREFS, false);
	}

	/**
	 * Android 4.1.2 Api 中有对 Dex 做了限制，Dex 文件只能放在私有目录下
	 */
	public static File getDexDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_DEX, Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN);
	}

	/**
	 * 为了测试方便，暂时先放在SD卡上
	 * 
	 * 这个需要保护起来，避免用户误删
	 */
	public static File getDexAppDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_DEX_APP, false);
	}

	public static File getAppDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_APP, false);
	}
	
	public static File getBookDir(Context context) {
		return PathUtils.makeSubDir(context, PATH_SUB_DIR_BOOK, false);
	}
}
