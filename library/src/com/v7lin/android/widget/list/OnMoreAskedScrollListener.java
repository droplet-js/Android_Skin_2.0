package com.v7lin.android.widget.list;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class OnMoreAskedScrollListener implements OnScrollListener {

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount) {
			onMoreAsked(view.getAdapter().getCount(), view.getLastVisiblePosition());
		}
	}

	public abstract void onMoreAsked(int overallItemsCount, int lastVisibleItemPosition);
}
