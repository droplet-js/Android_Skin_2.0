package com.v7lin.android.env.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
class BitmapCompatHoneycombMr1 {

	public static int getAllocationByteCount(Bitmap bitmap) {
		return bitmap.getByteCount();
	}
}
