package com.v7lin.android.widget.tabbar.impl;

import android.view.View;

import com.v7lin.android.widget.tabbar.Tab;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
class OnTabClickListener implements View.OnClickListener {

	private Tab mTab;

	public OnTabClickListener(Tab tab) {
		super();
		this.mTab = tab;
	}

	@Override
	public void onClick(View v) {
		if (mTab != null) {
			mTab.select(true);
		}
	}
}
