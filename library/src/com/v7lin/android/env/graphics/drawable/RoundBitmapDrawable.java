package com.v7lin.android.env.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;

/**
 * 圆角 Drawable
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class RoundBitmapDrawable extends Drawable {

	private static final int DEFAULT_PAINT_FLAGS = Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG;

	private int mTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
	private Bitmap mBitmap;
	private int mGravity = Gravity.FILL;
	private Paint mPaint = new Paint(DEFAULT_PAINT_FLAGS);
	private BitmapShader mBitmapShader;
	private float mCornerRadius;
	private final Rect mDstRect = new Rect();
	private final RectF mDstRectF = new RectF();

	private boolean mApplyGravity = true;
	private int mBitmapWidth;
	private int mBitmapHeight;

	public RoundBitmapDrawable(Resources res, Bitmap bitmap) {
		super();
		if (res != null) {
			mTargetDensity = res.getDisplayMetrics().densityDpi;
		}
		mBitmap = bitmap;
		if (mBitmap != null) {
			computeBitmapSize();
			mBitmapShader = new BitmapShader(mBitmap, TileMode.CLAMP, TileMode.CLAMP);
		} else {
			mBitmapWidth = (mBitmapHeight = -1);
		}
	}

	private void computeBitmapSize() {
		mBitmapWidth = mBitmap.getScaledWidth(mTargetDensity);
		mBitmapHeight = mBitmap.getScaledHeight(mTargetDensity);
	}

	public final Paint getPaint() {
		return mPaint;
	}

	public final Bitmap getBitmap() {
		return mBitmap;
	}

	public void setTargetDensity(Canvas canvas) {
		setTargetDensity(canvas.getDensity());
	}

	public void setTargetDensity(DisplayMetrics metrics) {
		setTargetDensity(metrics.densityDpi);
	}

	public void setTargetDensity(int density) {
		if (mTargetDensity != density) {
			mTargetDensity = (density == 0 ? 160 : density);
			if (mBitmap != null) {
				computeBitmapSize();
			}
			invalidateSelf();
		}
	}

	public int getGravity() {
		return mGravity;
	}

	public void setGravity(int gravity) {
		if (mGravity != gravity) {
			mGravity = gravity;
			mApplyGravity = true;
			invalidateSelf();
		}
	}

	public void setAntiAlias(boolean aa) {
		mPaint.setAntiAlias(aa);
		invalidateSelf();
	}

	public boolean hasAntiAlias() {
		return mPaint.isAntiAlias();
	}

	public void setFilterBitmap(boolean filter) {
		mPaint.setFilterBitmap(filter);
		invalidateSelf();
	}

	public void setDither(boolean dither) {
		mPaint.setDither(dither);
		invalidateSelf();
	}

	void updateDstRect() {
		if (mApplyGravity) {
			Gravity.apply(mGravity, mBitmapWidth, mBitmapHeight, getBounds(), mDstRect);

			mDstRectF.set(mDstRect);
			mApplyGravity = false;
		}
	}

	public void setCornerRadius(float cornerRadius) {
		if (isGreaterThanZero(cornerRadius))
			mPaint.setShader(mBitmapShader);
		else {
			mPaint.setShader(null);
		}
		mCornerRadius = cornerRadius;
	}

	public float getCornerRadius() {
		return mCornerRadius;
	}

	@Override
	public int getIntrinsicWidth() {
		return mBitmapWidth;
	}

	@Override
	public int getIntrinsicHeight() {
		return mBitmapHeight;
	}

	@Override
	public void setAlpha(int alpha) {
		final int oldAlpha = mPaint.getAlpha();
		if (alpha != oldAlpha) {
			mPaint.setAlpha(alpha);
			invalidateSelf();
		}
	}

	public int getAlpha() {
		return mPaint.getAlpha();
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
		invalidateSelf();
	}

	@Override
	public int getOpacity() {
		if (mGravity != Gravity.FILL) {
			return PixelFormat.TRANSLUCENT;
		}
		Bitmap bm = mBitmap;
		return (bm == null) || (bm.hasAlpha()) || (mPaint.getAlpha() < 255) || (isGreaterThanZero(mCornerRadius)) ? PixelFormat.TRANSLUCENT : PixelFormat.OPAQUE;
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap bitmap = mBitmap;
		if (bitmap == null || bitmap.isRecycled()) {
			return;
		}

		updateDstRect();

		Paint paint = mPaint;
		Shader shader = paint.getShader();
		if (shader == null) {
			canvas.drawBitmap(bitmap, null, mDstRect, paint);
		} else {
			canvas.drawRoundRect(mDstRectF, mCornerRadius, mCornerRadius, paint);
		}
	}

	private boolean isGreaterThanZero(float toCompare) {
		return Float.compare(toCompare, 0.0F) > 0;
	}
}
