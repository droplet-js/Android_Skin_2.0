package com.v7lin.android.os;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StatFs;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class StatFsCompatJellyBeanMr2 {

    private StatFsCompatJellyBeanMr2() {
        super();
    }

    public static long getBlockSizeLong(StatFs statfs) {
		return statfs.getBlockSizeLong();
	}

	public static long getBlockCountLong(StatFs statfs) {
		return statfs.getBlockCountLong();
	}

	public static long getAvailableBlocksLong(StatFs statfs) {
		return statfs.getAvailableBlocksLong();
	}

}
