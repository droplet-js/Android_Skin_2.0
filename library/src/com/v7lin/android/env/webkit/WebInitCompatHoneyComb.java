package com.v7lin.android.env.webkit;

import android.annotation.TargetApi;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class WebInitCompatHoneyComb {
	
	public static void setLayerType(View view, int layerType, Paint paint) {
		view.setLayerType(layerType, paint);
	}
}
