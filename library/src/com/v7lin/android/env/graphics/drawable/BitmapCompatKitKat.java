package com.v7lin.android.env.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class BitmapCompatKitKat {

	public static int getAllocationByteCount(Bitmap bitmap) {
		return bitmap.getAllocationByteCount();
	}
}
