package com.v7lin.android.env.graphics.drawable;

import android.graphics.Bitmap;
import android.os.Build;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class BitmapCompat {

	interface BitmapImpl {
		public boolean hasMipMap(Bitmap paramBitmap);

		public void setHasMipMap(Bitmap paramBitmap, boolean paramBoolean);

		public int getAllocationByteCount(Bitmap paramBitmap);
	}

	static class BaseBitmapImpl implements BitmapCompat.BitmapImpl {
		public boolean hasMipMap(Bitmap bitmap) {
			return false;
		}

		public void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
		}

		public int getAllocationByteCount(Bitmap bitmap) {
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
	}

	static class HcMr1BitmapCompatImpl extends BitmapCompat.BaseBitmapImpl {
		public int getAllocationByteCount(Bitmap bitmap) {
			return BitmapCompatHoneycombMr1.getAllocationByteCount(bitmap);
		}
	}

	static class JbMr2BitmapCompatImpl extends BitmapCompat.HcMr1BitmapCompatImpl {
		public boolean hasMipMap(Bitmap bitmap) {
			return BitmapCompatJellybeanMR2.hasMipMap(bitmap);
		}

		public void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
			BitmapCompatJellybeanMR2.setHasMipMap(bitmap, hasMipMap);
		}
	}

	static class KitKatBitmapCompatImpl extends BitmapCompat.JbMr2BitmapCompatImpl {
		public int getAllocationByteCount(Bitmap bitmap) {
			return BitmapCompatKitKat.getAllocationByteCount(bitmap);
		}
	}

	static final BitmapImpl IMPL;

	static {
		int version = Build.VERSION.SDK_INT;
		if (version >= Build.VERSION_CODES.KITKAT) {
			IMPL = new KitKatBitmapCompatImpl();
		} else if (version >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			IMPL = new JbMr2BitmapCompatImpl();
		} else if (version >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			IMPL = new HcMr1BitmapCompatImpl();
		} else {
			IMPL = new BaseBitmapImpl();
		}
	}

	public static boolean hasMipMap(Bitmap bitmap) {
		return IMPL.hasMipMap(bitmap);
	}

	public static void setHasMipMap(Bitmap bitmap, boolean hasMipMap) {
		IMPL.setHasMipMap(bitmap, hasMipMap);
	}

	public static int getAllocationByteCount(Bitmap bitmap) {
		return IMPL.getAllocationByteCount(bitmap);
	}

}
