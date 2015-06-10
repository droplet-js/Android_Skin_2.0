package com.v7lin.android.env.widget;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import com.v7lin.android.env.EnvCallback;
import com.v7lin.android.env.EnvRes;
import com.v7lin.android.env.EnvResourcesHelper;
import com.v7lin.android.env.EnvTypedArray;

/**
 * 采用 Region.Op.XOR 方案会有 BUG，故而变更方案
 * 
 * ProgressDrawable 中使用 ScaleDrawable，在小米 1 上显示不正常
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class CompatProgressBar extends ProgressBar implements EnvCallback {

	private static final int MAX_LEVEL = generateMaxLevel();

	private static int generateMaxLevel() {
		try {
			Field field = ProgressBar.class.getField("MAX_LEVEL");
			if (field != null) {
				field.setAccessible(true);
				int maxLevel = field.getInt(null);
				return maxLevel;
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return 10000;
	}

	private NumberHelper mNumberHelper;
	private EnvUIChanger<CompatProgressBar> mEnvUIChanger;

	public CompatProgressBar(Context context) {
		this(context, null);
	}

	public CompatProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.progressBarStyle);
	}

	public CompatProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mNumberHelper = new NumberHelper();

		mEnvUIChanger = new EnvCompatProgressBarChanger<CompatProgressBar>();
		mEnvUIChanger.applyStyle(context, attrs, defStyle, 0, false);
	}

	@Override
	public void scheduleSkin() {
		mEnvUIChanger.scheduleSkin(this);
	}

	@Override
	public void scheduleFont() {
		mEnvUIChanger.scheduleFont(this);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mEnvUIChanger.scheduleSkin(this);
		mEnvUIChanger.scheduleFont(this);
	}

	class EnvCompatProgressBarChanger<CPB extends CompatProgressBar> extends EnvProgressBarChanger<CPB> {

		private EnvRes mTextColorEnvRes;

		public EnvCompatProgressBarChanger() {
			super();
		}

		@Override
		public void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, boolean allowSysRes) {
			super.applyStyle(context, attrs, defStyleAttr, defStyleRes, allowSysRes);
			EnvTypedArray array = EnvTypedArray.obtainStyledAttributes(context, attrs, com.android.internal.R.styleable.TextView, defStyleAttr, defStyleRes);
			EnvRes textAppearanceEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_textAppearance, allowSysRes);
			if (textAppearanceEnvRes != null) {
				EnvTypedArray textAppearanceArray = EnvTypedArray.obtainStyledAttributes(context, textAppearanceEnvRes.getResid(), com.android.internal.R.styleable.TextAppearance);
				mTextColorEnvRes = textAppearanceArray.getEnvRes(com.android.internal.R.styleable.TextAppearance_textColor, allowSysRes);
				textAppearanceArray.recycle();
			}
			mTextColorEnvRes = array.getEnvRes(com.android.internal.R.styleable.TextView_textColor, mTextColorEnvRes, allowSysRes);
			int textSize = 15;
			textSize = array.getDimensionPixelSize(com.android.internal.R.styleable.TextView_textSize, textSize);
			mNumberHelper.setTextSize(textSize);
			array.recycle();
		}

		@Override
		protected void onScheduleSkin(CPB view) {
			super.onScheduleSkin(view);
			scheduleTextColor(view);
		}

		@Override
		protected void onScheduleFont(CPB view) {
			super.onScheduleFont(view);
			Typeface tf = EnvResourcesHelper.getFontFamily(view.getContext()).getTypeface();
			mNumberHelper.setTypeface(tf);
		}

		private void scheduleTextColor(CPB view) {
			if (mTextColorEnvRes != null) {
				ColorStateList textColor = getResources().getColorStateList(mTextColorEnvRes.getResid());
				mNumberHelper.setTextColor(textColor);
			}
		}
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mNumberHelper.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void setProgressDrawable(Drawable d) {
		super.setProgressDrawable(tileifyProgressDrawable(d));
	}

	private Drawable tileifyProgressDrawable(Drawable wrapped) {
		if (wrapped instanceof LayerDrawable) {
			LayerDrawable drawable = (LayerDrawable) wrapped;
			final int N = drawable.getNumberOfLayers();
			Drawable[] outDrawables = new Drawable[N];
			for (int i = 0; i < N; i++) {
				final int id = drawable.getId(i);
				Drawable childDrawable = drawable.getDrawable(i);
				if (id == android.R.id.background) {
					outDrawables[i] = new NumberBGDrawable(childDrawable);
				} else if (id == android.R.id.progress) {
					if (childDrawable instanceof ScaleDrawable) {
						outDrawables[i] = tileifyScaleDrawable((ScaleDrawable) childDrawable);
					} else if (childDrawable instanceof ClipDrawable) {
						outDrawables[i] = tileifyClipDrawable((ClipDrawable) childDrawable);
					} else {
						outDrawables[i] = childDrawable;
					}
				} else {
					outDrawables[i] = childDrawable;
				}
			}
			LayerDrawable newDrawable = new NumberLayerDrawable(outDrawables);
			return newDrawable;
		}
		return wrapped;
	}

	private Drawable tileifyScaleDrawable(ScaleDrawable wrapped) {
		try {
			Field mScaleStateField = ScaleDrawable.class.getDeclaredField("mScaleState");
			if (mScaleStateField != null) {
				mScaleStateField.setAccessible(true);
				Object mScaleState = mScaleStateField.get(wrapped);
				if (mScaleState != null) {
					Class<?> clazz = mScaleState.getClass();
					if (TextUtils.equals(clazz.getSimpleName(), "ScaleState")) {
						Drawable drawable = null;
						int gravity = Gravity.LEFT;
						float scaleWidth = 0;
						float scaleHeight = 0;

						Field mDrawableField = clazz.getDeclaredField("mDrawable");
						if (mDrawableField != null) {
							mDrawableField.setAccessible(true);
							drawable = (Drawable) mDrawableField.get(mScaleState);
						}
						Field mGravityField = clazz.getDeclaredField("mGravity");
						if (mGravityField != null) {
							mGravityField.setAccessible(true);
							gravity = mGravityField.getInt(mScaleState);
						}
						Field mScaleWidthField = clazz.getDeclaredField("mScaleWidth");
						if (mScaleWidthField != null) {
							mScaleWidthField.setAccessible(true);
							scaleWidth = mScaleWidthField.getFloat(mScaleState);
						}
						Field mScaleHeightField = clazz.getDeclaredField("mScaleHeight");
						if (mScaleHeightField != null) {
							mScaleHeightField.setAccessible(true);
							scaleHeight = mScaleHeightField.getFloat(mScaleState);
						}
						return new NumberScaleDrawable(drawable, gravity, scaleWidth, scaleHeight);
					}
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return wrapped;
	}

	private Drawable tileifyClipDrawable(ClipDrawable wrapped) {
		try {
			Field mClipStateField = ClipDrawable.class.getDeclaredField("mClipState");
			if (mClipStateField != null) {
				mClipStateField.setAccessible(true);
				Object mClipState = mClipStateField.get(wrapped);
				if (mClipState != null) {
					Class<?> clazz = mClipState.getClass();
					if (TextUtils.equals(clazz.getSimpleName(), "ClipState")) {
						Drawable drawable = null;
						int gravity = Gravity.LEFT;
						int orientation = ClipDrawable.HORIZONTAL;
						Field mDrawableField = clazz.getDeclaredField("mDrawable");
						if (mDrawableField != null) {
							mDrawableField.setAccessible(true);
							drawable = (Drawable) mDrawableField.get(mClipState);
						}
						Field mGravityField = clazz.getDeclaredField("mGravity");
						if (mGravityField != null) {
							mGravityField.setAccessible(true);
							gravity = mGravityField.getInt(mClipState);
						}
						Field mOrientationField = clazz.getDeclaredField("mOrientation");
						if (mOrientationField != null) {
							mOrientationField.setAccessible(true);
							orientation = mOrientationField.getInt(mClipState);
						}
						return new NumberClipDrawable(drawable, gravity, orientation);
					}
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return wrapped;
	}

	class NumberBGDrawable extends InsetDrawable implements DrawCallback {

		public NumberBGDrawable(Drawable drawable) {
			super(drawable, 0);
		}

		@Override
		public void draw(Canvas canvas) {
			if (mNumberHelper != null) {
				mNumberHelper.proxyDrawBG(canvas, this);
			} else {
				scheduleDraw(canvas);
			}
		}

		@Override
		public void scheduleDraw(Canvas canvas) {
			super.draw(canvas);
		}
	}

	class NumberClipDrawable extends ClipDrawable implements DrawCallback {

		public NumberClipDrawable(Drawable drawable, int gravity, int orientation) {
			super(drawable, gravity, orientation);
		}

		@Override
		public void draw(Canvas canvas) {
			if (mNumberHelper != null) {
				mNumberHelper.proxyDrawPG(canvas, this);
			} else {
				scheduleDraw(canvas);
			}
		}

		@Override
		public void scheduleDraw(Canvas canvas) {
			super.draw(canvas);
		}
	}

	class NumberScaleDrawable extends ScaleDrawable implements DrawCallback {

		public NumberScaleDrawable(Drawable drawable, int gravity, float scaleWidth, float scaleHeight) {
			super(drawable, gravity, scaleWidth, scaleHeight);
		}

		@Override
		public void draw(Canvas canvas) {
			if (mNumberHelper != null) {
				mNumberHelper.proxyDrawPG(canvas, this);
			} else {
				scheduleDraw(canvas);
			}
		}

		@Override
		public void scheduleDraw(Canvas canvas) {
			super.draw(canvas);
		}
	}

	class NumberLayerDrawable extends LayerDrawable implements LevelCallback {

		public NumberLayerDrawable(Drawable[] layers) {
			super(layers);
		}

		@Override
		protected boolean onLevelChange(int level) {
			if (mNumberHelper != null && mNumberHelper.proxyLevelChange(level, this)) {
				invalidate();
				return true;
			}
			return super.onLevelChange(level);
		}

		@Override
		public boolean scheduleLevel(int level) {
			return super.onLevelChange(level);
		}
	}

	@Override
	public void setIndeterminateDrawable(Drawable d) {
		super.setIndeterminateDrawable(tileifyIndeterminateDrawable(d));
	}

	private Drawable tileifyIndeterminateDrawable(Drawable wrapped) {
		if (wrapped instanceof AnimationDrawable) {
			return tileifyAnimationDrawable((AnimationDrawable) wrapped);
		}
		return wrapped;
	}

	private Drawable tileifyAnimationDrawable(AnimationDrawable wrapped) {
		NumberAnimationDrawable drawable = new NumberAnimationDrawable();
		drawable.setOneShot(wrapped.isOneShot());
		final int N = wrapped.getNumberOfFrames();
		for (int i = 0; i < N; i++) {
			Drawable frame = wrapped.getFrame(i);
			int duration = wrapped.getDuration(i);
			drawable.addFrame(frame, duration);
		}
		return drawable;
	}

	class NumberAnimationDrawable extends AnimationDrawable implements DrawCallback {

		public NumberAnimationDrawable() {
			super();
		}

		@Override
		public void draw(Canvas canvas) {
			if (mNumberHelper != null) {
				mNumberHelper.proxyDrawIM(canvas, this);
			} else {
				scheduleDraw(canvas);
			}
		}

		@Override
		public void scheduleDraw(Canvas canvas) {
			super.draw(canvas);
		}
	}

	class NumberHelper {
		private static final String FORMAT_PROGRESS = "%1$d%%";
		private TextPaint mTextPaint;
		private int mCurTextColor;
		private ColorStateList mTextColor;
		private float mTextLRPad;
		private AtomicBoolean mIsMeasure = new AtomicBoolean(false);

		public NumberHelper() {
			super();

			setup();
		}

		private void setup() {
			mTextPaint = new TextPaint();
			mTextPaint.setAntiAlias(true);
			mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
			mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getContext().getResources().getDisplayMetrics()));
			mTextPaint.setTextAlign(Paint.Align.CENTER);

			mTextColor = ColorStateList.valueOf(Color.BLACK);

			mTextLRPad = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getContext().getResources().getDisplayMetrics());
		}

		public void setTextColor(ColorStateList color) {
			if (color != null) {
				mTextColor = color;
			} else {
				mTextColor = ColorStateList.valueOf(Color.BLACK);
			}
			updateTextColors();
		}

		public void setTextSize(float textSize) {
			if (mTextPaint.getTextSize() != textSize) {
				mTextPaint.setTextSize(textSize);
				rInvalidate();
			}
		}

		public void setTypeface(Typeface tf) {
			if (mTextPaint.getTypeface() != tf) {
				mTextPaint.setTypeface(tf);
				rInvalidate();
			}
		}

		private void updateTextColors() {
			boolean inval = false;
			int color = mTextColor.getColorForState(getDrawableState(), 0);
			if (color != mCurTextColor) {
				mCurTextColor = color;
				inval = true;
			}
			if (inval) {
				rInvalidate();
			}

		}

		private void rInvalidate() {

		}

		private boolean shouldDrawText() {
			return getProgress() > 0 && mIsMeasure.get();
		}

		private float generateProgress() {
			return getMax() > 0 ? getProgress() * 1.0f / getMax() : 0;
		}

		private String generateText() {
			final int progressInt = Math.round(generateProgress() * 100);
			return String.format(Locale.getDefault(), FORMAT_PROGRESS, progressInt);
		}

		private RectF generateTextRectF() {
			FontMetrics fontMetrics = mTextPaint.getFontMetrics();
			final float height = fontMetrics.bottom - fontMetrics.top;

			String text = generateText();
			final float width = Layout.getDesiredWidth(text, mTextPaint) + 2 * mTextLRPad;

			return new RectF(0, 0, width, height);
		}

		public void measure(int widthMeasureSpec, int heightMeasureSpec) {
			mIsMeasure.compareAndSet(false, true);
			if (shouldDrawText()) {
				final int measuredWidth = getMeasuredWidth();
				final int measuredHeight = getMeasuredHeight();
				RectF targetRectF = generateTextRectF();
				setMeasuredDimension(Math.max(measuredWidth, resolveSize(Math.round(targetRectF.width()) + getPaddingLeft() + getPaddingRight(), widthMeasureSpec)),
						Math.max(measuredHeight, resolveSize(Math.round(targetRectF.height()) + getPaddingTop() + getPaddingBottom(), heightMeasureSpec)));
			}
		}

		public void proxyDrawBG(Canvas canvas, DrawCallback callback) {
			if (shouldDrawText()) {
				final float progress = generateProgress();
				final RectF targetRectF = generateTextRectF();
				final float textWidth = targetRectF.width();
				final float width = getWidth();
				final float height = getHeight();

				// Drawable
				{
					final float left = width * progress + textWidth > width ? width : width * progress + textWidth;
					final float top = 0;
					final float right = width;
					final float bottom = height;
					canvas.save();
					canvas.clipRect(left, top, right, bottom);
					callback.scheduleDraw(canvas);
					canvas.restore();
				}
			} else {
				callback.scheduleDraw(canvas);
			}
		}

		public boolean proxyLevelChange(int level, LevelCallback callback) {
			if (shouldDrawText()) {
				final float progress = generateProgress();
				final RectF targetRectF = generateTextRectF();
				final float textWidth = targetRectF.width();
				final float width = getWidth();
				if (width * progress + textWidth > width) {
					int proxyLevel = Math.round((width - textWidth) * MAX_LEVEL / width);
					callback.scheduleLevel(proxyLevel);
					return true;
				}
			}
			return false;
		}

		public void proxyDrawPG(Canvas canvas, DrawCallback callback) {
			if (shouldDrawText()) {
				final float progress = generateProgress();
				final String text = generateText();
				final RectF targetRectF = generateTextRectF();
				final float textWidth = targetRectF.width();
				final float width = getWidth();
				final float height = getHeight();

				// Drawable
				{
					final float left = 0;
					final float top = 0;
					final float right = width * progress + textWidth > width ? width - textWidth : width * progress;
					final float bottom = height;

					canvas.save();
					canvas.clipRect(left, top, right, bottom);
					callback.scheduleDraw(canvas);
					canvas.restore();
				}
				// Text
				{
					final float left = width * progress + textWidth > width ? width - textWidth : width * progress;
					final float top = 0;
					final float right = left + textWidth;
					final float bottom = height;
					FontMetrics fontMetrics = mTextPaint.getFontMetrics();
					final float baseline = top + (bottom - top - fontMetrics.descent + fontMetrics.ascent) / 2 - fontMetrics.ascent;
					canvas.save();
					mCurTextColor = mTextColor.getColorForState(getDrawableState(), 0);
					mTextPaint.setColor(mCurTextColor);
					canvas.drawText(text, (left + right) * 0.5f, baseline, mTextPaint);
					canvas.restore();
				}
			} else {
				callback.scheduleDraw(canvas);
			}
		}

		public void proxyDrawIM(Canvas canvas, DrawCallback callback) {
			if (shouldDrawText()) {
				final float progress = generateProgress();
				final String text = generateText();
				final RectF targetRectF = generateTextRectF();
				final float textWidth = targetRectF.width();
				final float width = getWidth();
				final float height = getHeight();

				// Drawable
				{
					callback.scheduleDraw(canvas);
				}
				// Text
				{
					final float left = width * progress + textWidth > width ? width - textWidth : width * progress;
					final float top = 0;
					final float right = left + textWidth;
					final float bottom = height;
					FontMetrics fontMetrics = mTextPaint.getFontMetrics();
					final float baseline = top + (bottom - top - fontMetrics.descent + fontMetrics.ascent) / 2 - fontMetrics.ascent;
					canvas.save();
					canvas.drawText(text, (left + right) * 0.5f, baseline, mTextPaint);
					canvas.restore();
				}
			} else {
				callback.scheduleDraw(canvas);
			}
		}
	}

	interface DrawCallback {
		public void scheduleDraw(Canvas canvas);
	}

	interface LevelCallback {
		public boolean scheduleLevel(int level);
	}

	@Override
	public synchronized void setProgress(int progress) {
		// super.setProgress(progress);
		final int curr = getProgress();
		final int dest = Math.min(progress, getMax());
		if (curr != dest) {
			Animation anim = new SmoothProgressAnimation(curr, dest);
			anim.setDuration(1 * 1000);
			anim.setInterpolator(new AccelerateInterpolator());
			anim.setAnimationListener(mAnimationListener);
			startAnimation(anim);
		}
	}

	private synchronized void scheduleProgress(int progress) {
		super.setProgress(progress);
	}

	class SmoothProgressAnimation extends Animation {

		private int curr;
		private int dest;

		public SmoothProgressAnimation(int curr, int dest) {
			super();
			this.curr = curr;
			this.dest = dest;
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			final int progress = curr + Math.round((dest - curr) * interpolatedTime);
			scheduleProgress(progress);
		}
	}

	private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			clearAnimation();
		}
	};
}
