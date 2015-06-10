package com.v7lin.android.widget.list;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class OnLazyScrollListener implements OnScrollListener {

	private boolean mIsIdle = true;
	private OnScrollListener mWrapped;
	private LazyCallBack mCallBack;

	public OnLazyScrollListener(OnScrollListener wrapped, LazyCallBack callback) {
		super();
		mWrapped = wrapped;
		mCallBack = callback;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE: {
			mCallBack.setIsIdle(true);

			final int childCount = view.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = view.getChildAt(i);
				Object tag = child.getTag();
				if (tag != null && tag instanceof LazyHolder<?>) {
					LazyHolder<?> holder = (LazyHolder<?>) tag;
					holder.loadImage(mIsIdle);// 滑动空闲时，加载图片，优化用户体验
				}
			}
			break;
		}
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: {
			mCallBack.setIsIdle(false);
			break;
		}
		case OnScrollListener.SCROLL_STATE_FLING: {
			mCallBack.setIsIdle(false);
			break;
		}
		default: {
			break;
		}
		}
		if (mWrapped != null) {
			mWrapped.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mWrapped != null) {
			mWrapped.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}
};
