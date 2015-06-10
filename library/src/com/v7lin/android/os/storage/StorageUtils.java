package com.v7lin.android.os.storage;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.v7lin.android.os.StatFsCompat;

/**
 * 存储设备管理管理 优先级：SdCard > Memory
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public final class StorageUtils {

	/** 存储器最小存储空间为 50M */
	private static final long MIN_SIZE = 50 * 1024 * 1024;

	private StorageUtils() {
		super();
	}

	/**
	 * 获取绝对路径
	 */
	public static File getStorageDir(Context context, boolean isPrivate) {
		File storageDir = null;
		if (!isPrivate) {
			StorageManagerCompat manager = StorageManagerCompat.get(context);
			String[] paths = manager.getVolumePaths();
			if (paths != null) {
				String storageDirPath = null;
				final int length = paths.length;
				for (int i = 0; i < length; i++) {
					final String path = paths[i];
					final long totalSpace = StorageUtils.getTotalSpace(path);
					if (totalSpace >= MIN_SIZE) {
						storageDirPath = path;
						break;
					}
				}
				if (TextUtils.isEmpty(storageDirPath) && length > 0) {
					storageDirPath = paths[0];
				}
				if (!TextUtils.isEmpty(storageDirPath)) {
					storageDir = new File(storageDirPath);
				}
			}
			if (storageDir == null) {
				storageDir = StorageUtils.hasExternalStorage() ? StorageUtils.getSDCardStorageDir(context) : StorageUtils.getMemoryStorageDir(context);
			}
		} else {
			storageDir = StorageUtils.getMemoryStorageDir(context);
		}
		return storageDir;
	}

	/**
	 * 获取 basePath 总空间大小
	 */
	private static long getTotalSpace(String path) {
		StatFsCompat statFs = StatFsCompat.get(path);
		final long blockCount = statFs.getBlockCountLong();
		final long blockSize = statFs.getBlockSizeLong();
		return blockCount * blockSize;
	}

	/**
	 * 是否有正在挂载的多媒体设备
	 */
	private static boolean hasExternalStorage() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡上的绝对路径
	 */
	private static File getSDCardStorageDir(Context context) {
		return Environment.getExternalStorageDirectory();
	}

	/**
	 * 获取内存上的绝对路径
	 */
	private static File getMemoryStorageDir(Context context) {
		return context.getFilesDir();
	}
}
