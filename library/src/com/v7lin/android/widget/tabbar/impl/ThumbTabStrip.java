package com.v7lin.android.widget.tabbar.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.v7lin.android.env.widget.CompatHorizontalScrollView;
import com.v7lin.android.widget.tabbar.Tab;
import com.v7lin.android.widget.tabbar.TabConst;
import com.v7lin.android.widget.tabbar.TabStrip;
import com.v7lin.android.widget.tabbar.TabWrapper;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
@SuppressWarnings("deprecation")
public class ThumbTabStrip extends CompatHorizontalScrollView implements TabStrip, TabConst {

	private LinearTabStrip mTabStripWrapper;

	private int mScrollOffset;

	private int mIndicatorHeight;
	private int mIndicatorColor;
	private Paint mIndicatorPaint;

	private boolean mAutoAnimated = true;

	private int mLastScrollX = 0;
	private int mCurPos = INVALID_POSITION;
	private float mCurPosOffset = 0f;

	private boolean mShouldExp;
	private boolean mCheckedTabWidths;
	private boolean mCheckedMeasure;

	private LinearLayout.LayoutParams mExpTabLayoutParams;

	public ThumbTabStrip(Context context) {
		super(context);

		setup();
	}

	public ThumbTabStrip(Context context, AttributeSet attrs) {
		super(context, attrs);

		setup();
	}

	public ThumbTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setup();
	}

	private void setup() {
		initData();
		initView();
	}

	private void initData() {
		mShouldExp = false;
		mCheckedTabWidths = false;
		mCheckedMeasure = false;
	}

	private void initView() {
		super.setFillViewport(true);
		super.setWillNotDraw(false);

		mTabStripWrapper = new LinearTabStrip(getContext());
		mTabStripWrapper.setOrientation(LinearLayout.HORIZONTAL);
		mTabStripWrapper.setGravity(Gravity.CENTER_VERTICAL);

		HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(HorizontalScrollView.LayoutParams.FILL_PARENT, HorizontalScrollView.LayoutParams.FILL_PARENT);
		addView(mTabStripWrapper, params);

		mScrollOffset = complexToDimensionPixelSize(getContext(), 52);

		mIndicatorHeight = complexToDimensionPixelSize(getContext(), 3);

		mIndicatorColor = 0xFF666666;

		mIndicatorPaint = new Paint();
		mIndicatorPaint.setAntiAlias(true);
		mIndicatorPaint.setStyle(Style.FILL);
		mIndicatorPaint.setColor(mIndicatorColor);

		mExpTabLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1.0f);
	}

	private int complexToDimensionPixelSize(Context context, float value) {
		return Math.round(applyDimension(context, value));
	}

	private float applyDimension(Context context, float value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
	}

	public void setShouldExpand(boolean shouldExpand) {
		mShouldExp = shouldExpand;
		requestLayout();
	}

	public void setScrollOffset(int offset) {
		mScrollOffset = offset;
		invalidate();
	}

	public void setIndicatorHeight(int height) {
		mIndicatorHeight = height;
		invalidate();
	}

	public void setIndicatorColor(int color) {
		mIndicatorColor = color;
		mIndicatorPaint.setColor(color);
		invalidate();
	}

	public void setAutoAnimated(boolean animated) {
		mAutoAnimated = animated;
	}

	@Override
	public void setDividerDrawableSupport(Drawable divider) {
		mTabStripWrapper.setDividerDrawableSupport(divider);
	}

	@Override
	public void setShowDividersSupport(int showDividers) {
		mTabStripWrapper.setShowDividersSupport(showDividers);
	}

	@Override
	public void setDividerPaddingSupport(int padding) {
		mTabStripWrapper.setDividerPaddingSupport(padding);
	}

	@Override
	public int getTabCount() {
		return mTabStripWrapper.getTabCount();
	}

	@Override
	public Tab wrap(Tab tab) {
		return new ThumbTabWrapper(tab);
	}

	class ThumbTabWrapper extends TabWrapper {

		public ThumbTabWrapper(Tab wrapper) {
			super(wrapper);
		}

		@Override
		public void select(boolean fromUser) {
			super.select(fromUser);
			if (!isOverflow() || (!mAutoAnimated && mCheckedMeasure)) {
				return;
			}
			if (mCheckedMeasure) {
				final int position = getPosition();
				animateToTab(position, 0);
			} else {
				getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						getViewTreeObserver().removeGlobalOnLayoutListener(this);
						final int position = getPosition();
						animateToTab(position, 0);
					}
				});
			}
		}
	}

	@Override
	public void addTab(Tab tab) {
		mCheckedTabWidths = false;
		mCheckedMeasure = false;
		mTabStripWrapper.addTab(tab);
	}

	@Override
	public void addTab(Tab tab, int position) {
		mCheckedTabWidths = false;
		mCheckedMeasure = false;
		mTabStripWrapper.addTab(tab, position);
	}

	@Override
	public View removeTabAt(int position) {
		mCheckedTabWidths = false;
		mCheckedMeasure = false;
		return mTabStripWrapper.removeTabAt(position);
	}

	@Override
	public void animateToTab(int position, float positionOffset) {
		final int tabCount = mTabStripWrapper.getTabCount();
		if (tabCount == 0 || position < 0) {
			return;
		}

		mCurPos = position;
		mCurPosOffset = positionOffset;

		int offset = mTabStripWrapper.getTabCount() > position ? (int) (positionOffset * mTabStripWrapper.getTabViewAt(position).getWidth()) : 0;
		int newScrollX = mTabStripWrapper.getTabViewAt(position).getLeft() + offset;

		if (position > 0 || offset > 0) {
			newScrollX -= mScrollOffset;
		}

		if (newScrollX != mLastScrollX) {
			mLastScrollX = newScrollX;
			scrollTo(newScrollX, 0);
		}

		invalidate();
	}

	@Override
	public void setTabSelected(int position) {
		mTabStripWrapper.setTabSelected(position);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mCheckedMeasure = true;
		if (!mShouldExp || MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
			return;
		}
		if (!mCheckedTabWidths) {
			final int tabCount = mTabStripWrapper.getTabCount();
			final int measuredWidth = getMeasuredWidth();
			int childWidth = 0;
			for (int i = 0; i < tabCount; i++) {
				childWidth += mTabStripWrapper.getTabViewAt(i).getMeasuredWidth();
			}
			if (childWidth > 0 && measuredWidth > 0) {
				if (childWidth <= measuredWidth) {
					for (int i = 0; i < tabCount; i++) {
						mTabStripWrapper.getTabViewAt(i).setLayoutParams(mExpTabLayoutParams);
					}
				}
				mCheckedTabWidths = true;
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		final int tabCount = mTabStripWrapper.getTabCount();
		if (isInEditMode() || tabCount == 0) {
			return;
		}
		View currentTabView = mTabStripWrapper.getTabViewAt(mCurPos);
		if (currentTabView == null) {
			return;
		}
		float lineLeft = currentTabView.getLeft();
		float lineRight = currentTabView.getRight();
		final int height = getHeight();
		// if there is an offset, start interpolating left and right coordinates
		// between current and next tab
		if (mCurPosOffset > 0f && mCurPos < tabCount - 1) {
			View nextTabView = mTabStripWrapper.getTabViewAt(mCurPos + 1);
			final float nextTabLeft = nextTabView.getLeft();
			final float nextTabRight = nextTabView.getRight();

			lineLeft = (mCurPosOffset * nextTabLeft + (1f - mCurPosOffset) * lineLeft);
			lineRight = (mCurPosOffset * nextTabRight + (1f - mCurPosOffset) * lineRight);
		}
		canvas.drawRect(lineLeft, height - mIndicatorHeight, lineRight, height, mIndicatorPaint);
	}
}
