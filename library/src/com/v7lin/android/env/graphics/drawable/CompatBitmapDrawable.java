package com.v7lin.android.env.graphics.drawable;

import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

/**
 * 为了防止 Bitmap 被回收引发崩溃，重写 BitmapDrawable
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatBitmapDrawable extends BitmapDrawable {

	private static final String TAG = "CompatBitmapDrawable";

	public CompatBitmapDrawable(Resources res, Bitmap bitmap) {
		super(res, bitmap);
	}

	public CompatBitmapDrawable(Resources res, InputStream is) {
		super(res, is);
	}

	public CompatBitmapDrawable(Resources res, String filepath) {
		super(res, filepath);
	}

	@Override
	public void draw(Canvas canvas) {
		try {
			Bitmap bitmap = getBitmap();
			if (bitmap != null && !bitmap.isRecycled()) {
				super.draw(canvas);
			} else {
				Log.e(TAG, "$$$ Bitmap is recycled.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
