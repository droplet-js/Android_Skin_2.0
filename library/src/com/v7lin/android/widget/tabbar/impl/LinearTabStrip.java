package com.v7lin.android.widget.tabbar.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.v7lin.android.env.widget.CompatLinearLayout;
import com.v7lin.android.widget.tabbar.Tab;
import com.v7lin.android.widget.tabbar.TabConst;
import com.v7lin.android.widget.tabbar.TabFactory;
import com.v7lin.android.widget.tabbar.TabStrip;

/**
 * 线性的 TabBar
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class LinearTabStrip extends CompatLinearLayout implements TabStrip, TabConst {

	public LinearTabStrip(Context context) {
		super(context);

		setup();
	}

	public LinearTabStrip(Context context, AttributeSet attrs) {
		super(context, attrs);

		setup();
	}

	private void setup() {
		initData();
		initView();
	}

	private void initData() {

	}

	private void initView() {

	}

	@Override
	public int getTabCount() {
		return getChildCount();
	}

	@Override
	public Tab wrap(Tab tab) {
		return tab;
	}

	@Override
	public void addTab(Tab tab) {
		View tabView = createTabView(tab);
		if (tabView != null) {
			addView(tabView);
		}
	}

	@Override
	public void addTab(Tab tab, int position) {
		View tabView = createTabView(tab);
		if (tabView != null) {
			addView(tabView, position);
		}
	}

	private View createTabView(Tab tab) {
		View tabView = null;
		if (tab != null) {
			TabFactory factory = tab.getTabFactory();
			if (factory != null) {
				tabView = factory.createTabView(getContext(), tab.getInitialTab());
				if (tabView != null) {
					tabView.setOnClickListener(new OnTabClickListener(tab));
				}
			}
		}
		return tabView;
	}

	@Override
	public View removeTabAt(int position) {
		View tabView = getChildAt(position);
		removeView(tabView);
		return tabView;
	}

	@Override
	public void animateToTab(int position, float positionOffset) {

	}

	@Override
	public void setTabSelected(int position) {
		int tabCount = getChildCount();
		for (int i = 0; i < tabCount; i++) {
			View tabView = getTabViewAt(i);
			tabView.setSelected(i == position);
		}
	}

	public View getTabViewAt(int position) {
		return getChildAt(position);
	}
}
