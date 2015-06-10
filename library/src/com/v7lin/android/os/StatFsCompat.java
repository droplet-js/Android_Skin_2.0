package com.v7lin.android.os;

import android.os.Build;
import android.os.StatFs;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class StatFsCompat {

	interface StatFsCompatImpl {
		public long getBlockSizeLong(StatFs statfs);

		public long getBlockCountLong(StatFs statfs);

		public long getAvailableBlocksLong(StatFs statfs);
	}
	
	static class EarlyStatFsCompatImpl implements StatFsCompatImpl {

		@SuppressWarnings("deprecation")
		@Override
		public long getBlockSizeLong(StatFs statfs) {
			return statfs.getBlockSize();
		}

		@SuppressWarnings("deprecation")
		@Override
		public long getBlockCountLong(StatFs statfs) {
			return statfs.getBlockCount();
		}

		@SuppressWarnings("deprecation")
		@Override
		public long getAvailableBlocksLong(StatFs statfs) {
			return statfs.getAvailableBlocks();
		}
	}
	
	static class JellyBeanMr2StatFsCompatImpl implements StatFsCompatImpl {

		@Override
		public long getBlockSizeLong(StatFs statfs) {
			return StatFsCompatJellyBeanMr2.getBlockSizeLong(statfs);
		}

		@Override
		public long getBlockCountLong(StatFs statfs) {
			return StatFsCompatJellyBeanMr2.getBlockCountLong(statfs);
		}

		@Override
		public long getAvailableBlocksLong(StatFs statfs) {
			return StatFsCompatJellyBeanMr2.getAvailableBlocksLong(statfs);
		}
	}
	
	public static StatFsCompat get(String path) {
		return new StatFsCompat(path);
	} 
	
	private StatFs statFs;
	private final StatFsCompatImpl impl;
	
	private StatFsCompat(String path) {
		this(Build.VERSION.SDK_INT, path);
	}
	
	private StatFsCompat(int apiVersion, String path) {
		super();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			impl = new JellyBeanMr2StatFsCompatImpl();
		} else {
			impl = new EarlyStatFsCompatImpl();
		}
		statFs = new StatFs(path);
	}
	
	public long getBlockSizeLong() {
		return impl.getBlockSizeLong(statFs);
	}

	public long getBlockCountLong() {
		return impl.getBlockCountLong(statFs);
	}

	public long getAvailableBlocksLong() {
		return impl.getAvailableBlocksLong(statFs);
	}

}
