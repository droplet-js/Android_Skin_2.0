package com.v7lin.android.env.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressLint("InlinedApi")
class LinearLayoutCompat {

	public static final int SHOW_DIVIDER_NONE_SUPPORT = LinearLayoutCompat.isUseSupport() ? 0 : LinearLayout.SHOW_DIVIDER_NONE;
	public static final int SHOW_DIVIDER_BEGINNING_SUPPORT = LinearLayoutCompat.isUseSupport() ? 1 : LinearLayout.SHOW_DIVIDER_BEGINNING;
	public static final int SHOW_DIVIDER_MIDDLE_SUPPORT = LinearLayoutCompat.isUseSupport() ? 2 : LinearLayout.SHOW_DIVIDER_MIDDLE;
	public static final int SHOW_DIVIDER_END_SUPPORT = LinearLayoutCompat.isUseSupport() ? 4 : LinearLayout.SHOW_DIVIDER_END;

	private static boolean isUseSupport() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	interface LinearLayoutCompatImpl {
		public void setDividerDrawableSupport(Drawable divider);

		public void setShowDividersSupport(int showDividers);

		public void setDividerPaddingSupport(int padding);

		public void measureChildWithMarginsSupport(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed);

		public void onDrawSupport(Canvas canvas);
	}

	static class EarlyLinearLayoutCompatImpl implements LinearLayoutCompatImpl {

		private LinearLayout mLinearLayout;

		private Drawable mDivider;
		private int mDividerWidth;
		private int mDividerHeight;
		private int mShowDividers;
		private int mDividerPadding;

		public EarlyLinearLayoutCompatImpl(LinearLayout layout) {
			super();
			mLinearLayout = layout;
		}

		@Override
		public void setDividerDrawableSupport(Drawable divider) {
			mDivider = divider;
			if (divider != null) {
				mDividerWidth = divider.getIntrinsicWidth();
				mDividerHeight = divider.getIntrinsicHeight();
			} else {
				mDividerWidth = 0;
				mDividerHeight = 0;
			}
			mLinearLayout.setWillNotDraw(divider == null);
			mLinearLayout.requestLayout();
		}

		@Override
		public void setShowDividersSupport(int showDividers) {
			mShowDividers = showDividers;
			mLinearLayout.requestLayout();
		}

		@Override
		public void setDividerPaddingSupport(int padding) {
			mDividerPadding = padding;
			mLinearLayout.requestLayout();
		}

		@Override
		public void measureChildWithMarginsSupport(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
			if (mDivider != null) {
				int childIndex = mLinearLayout.indexOfChild(child);
				int count = mLinearLayout.getChildCount();
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();

				if (mLinearLayout.getOrientation() == LinearLayout.VERTICAL) {
					if (hasDividerBeforeChildAt(childIndex)) {
						params.topMargin = mDividerHeight;
					} else if ((childIndex == count - 1) && (hasDividerBeforeChildAt(count))) {
						params.bottomMargin = mDividerHeight;
					}
				} else if (hasDividerBeforeChildAt(childIndex)) {
					params.leftMargin = mDividerWidth;
				} else if ((childIndex == count - 1) && (hasDividerBeforeChildAt(count))) {
					params.rightMargin = mDividerWidth;
				}
			}
		}

		private boolean hasDividerBeforeChildAt(int childIndex) {
			if (childIndex == 0)
				return (mShowDividers & SHOW_DIVIDER_BEGINNING_SUPPORT) != 0;
			if (childIndex == mLinearLayout.getChildCount())
				return (mShowDividers & SHOW_DIVIDER_END_SUPPORT) != 0;
			if ((mShowDividers & SHOW_DIVIDER_MIDDLE_SUPPORT) != 0) {
				boolean hasVisibleViewBefore = false;
				for (int i = childIndex - 1; i >= 0; i--) {
					if (mLinearLayout.getChildAt(i).getVisibility() != View.GONE) {
						hasVisibleViewBefore = true;
						break;
					}
				}
				return hasVisibleViewBefore;
			}
			return false;
		}

		@Override
		public void onDrawSupport(Canvas canvas) {
			if (mDivider == null) {
				return;
			}

			if (mLinearLayout.getOrientation() == LinearLayout.VERTICAL) {
				drawDividersVertical(canvas);
			} else {
				drawDividersHorizontal(canvas);
			}
		}

		private void drawDividersVertical(Canvas canvas) {
			int count = mLinearLayout.getChildCount();
			for (int i = 0; i < count; i++) {
				View child = mLinearLayout.getChildAt(i);
				if ((child == null) || (child.getVisibility() == View.GONE) || (!hasDividerBeforeChildAt(i))) {
					continue;
				}
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
				drawHorizontalDivider(canvas, child.getTop() - lp.topMargin);
			}

			if (hasDividerBeforeChildAt(count)) {
				View child = mLinearLayout.getChildAt(count - 1);
				int bottom = 0;
				if (child == null) {
					bottom = mLinearLayout.getHeight() - mLinearLayout.getPaddingBottom() - mDividerHeight;
				} else {
					bottom = child.getBottom();
				}
				drawHorizontalDivider(canvas, bottom);
			}
		}

		private void drawDividersHorizontal(Canvas canvas) {
			int count = mLinearLayout.getChildCount();
			for (int i = 0; i < count; i++) {
				View child = mLinearLayout.getChildAt(i);
				if ((child == null) || (child.getVisibility() == View.GONE) || (!hasDividerBeforeChildAt(i)))
					continue;
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
				drawVerticalDivider(canvas, child.getLeft() - lp.leftMargin);
			}

			if (hasDividerBeforeChildAt(count)) {
				View child = mLinearLayout.getChildAt(count - 1);
				int right = 0;
				if (child == null)
					right = mLinearLayout.getWidth() - mLinearLayout.getPaddingRight() - mDividerWidth;
				else {
					right = child.getRight();
				}
				drawVerticalDivider(canvas, right);
			}
		}

		private void drawHorizontalDivider(Canvas canvas, int top) {
			mDivider.setBounds(mLinearLayout.getPaddingLeft() + mDividerPadding, top, mLinearLayout.getWidth() - mLinearLayout.getPaddingRight() - mDividerPadding, top + mDividerHeight);
			mDivider.draw(canvas);
		}

		private void drawVerticalDivider(Canvas canvas, int left) {
			mDivider.setBounds(left, mLinearLayout.getPaddingTop() + mDividerPadding, left + mDividerWidth, mLinearLayout.getHeight() - mLinearLayout.getPaddingBottom() - mDividerPadding);
			mDivider.draw(canvas);
		}
	}

	static class IceCreamSandwichLinearLayoutCompatImpl implements LinearLayoutCompatImpl {

		private LinearLayout mLinearLayout;

		public IceCreamSandwichLinearLayoutCompatImpl(LinearLayout layout) {
			super();
			mLinearLayout = layout;
		}

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public void setDividerDrawableSupport(Drawable divider) {
			mLinearLayout.setDividerDrawable(divider);
		}

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public void setShowDividersSupport(int showDividers) {
			mLinearLayout.setShowDividers(showDividers);
		}

		@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		@Override
		public void setDividerPaddingSupport(int padding) {
			mLinearLayout.setDividerPadding(padding);
		}

		@Override
		public void measureChildWithMarginsSupport(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
			// do nothing
		}

		@Override
		public void onDrawSupport(Canvas canvas) {
			// do nothing
		}
	}

	public static LinearLayoutCompat get(LinearLayout layout) {
		return new LinearLayoutCompat(layout);
	}

	private final LinearLayoutCompatImpl mImpl;

	private LinearLayoutCompat(LinearLayout layout) {
		super();
		if (LinearLayoutCompat.isUseSupport()) {
			mImpl = new EarlyLinearLayoutCompatImpl(layout);
		} else {
			mImpl = new IceCreamSandwichLinearLayoutCompatImpl(layout);
		}
	}

	public void setDividerDrawableSupport(Drawable divider) {
		mImpl.setDividerDrawableSupport(divider);
	}

	public void setShowDividersSupport(int showDividers) {
		mImpl.setShowDividersSupport(showDividers);
	}

	public void setDividerPaddingSupport(int padding) {
		mImpl.setDividerPaddingSupport(padding);
	}

	public void measureChildWithMarginsSupport(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
		mImpl.measureChildWithMarginsSupport(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
	}

	public void onDrawSupport(Canvas canvas) {
		mImpl.onDrawSupport(canvas);
	}
}
