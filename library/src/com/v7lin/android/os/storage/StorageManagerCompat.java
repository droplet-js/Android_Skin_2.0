package com.v7lin.android.os.storage;

import android.Manifest;
import android.content.Context;
import android.os.Build;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
class StorageManagerCompat {

	interface StorageManagerCompatImpl {
		public String[] getVolumePaths(Context context);
	}

	static class EarlyStorageManagerCompatImpl implements StorageManagerCompatImpl {

		@Override
		public String[] getVolumePaths(Context context) {
			return null;
		}
	}

	static class HoneyCombStorageManagerCompatImpl implements StorageManagerCompatImpl {

		@Override
		public String[] getVolumePaths(Context context) {
			return StorageManagerCompatHoneyComb.getVolumePaths(context);
		}
	}

	public static StorageManagerCompat get(Context context) {
		return new StorageManagerCompat(context);
	}

	private final StorageManagerCompatImpl mImpl;
	private final Context mContext;

	private StorageManagerCompat(Context context) {
		this(Build.VERSION.SDK_INT, context);
	}
	
	private StorageManagerCompat(int apiVersion, Context context) {
		super();
		if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
			mImpl = new HoneyCombStorageManagerCompatImpl();
		} else {
			mImpl = new EarlyStorageManagerCompatImpl();
		}
		mContext = context;
	}
	
	public String[] getVolumePaths() {
		return mImpl.getVolumePaths(mContext);
	}
}
