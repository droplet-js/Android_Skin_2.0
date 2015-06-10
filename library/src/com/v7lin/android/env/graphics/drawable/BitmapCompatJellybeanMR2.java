package com.v7lin.android.env.graphics.drawable;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class BitmapCompatJellybeanMR2 {

	public static boolean hasMipMap(Bitmap bitmap) {
		return bitmap.hasMipMap();
	}

	public static void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
		bitmap.setHasMipMap(hasMipMap);
	}
}
