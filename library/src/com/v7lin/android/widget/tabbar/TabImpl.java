package com.v7lin.android.widget.tabbar;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
class TabImpl extends Tab {

	private TabBarImpl mTabBar;
	private int mPosition;
	private boolean mIsOverflow = false;
	private TabFactory mTabFactory;
	private OnTabChangedListener mOnTabChangedListener;

	TabImpl(TabBarImpl tabBar) {
		super();
		mTabBar = tabBar;
	}

	@Override
	void setPosition(int position) {
		mPosition = position;
	}

	@Override
	public int getPosition() {
		return mPosition;
	}

	@Override
	public void setOverflow(boolean enable) {
		mIsOverflow = enable;
	}

	@Override
	public boolean isOverflow() {
		return mIsOverflow;
	}

	@Override
	public void setTabFactory(TabFactory factory) {
		mTabFactory = factory;
	}

	@Override
	public TabFactory getTabFactory() {
		return mTabFactory;
	}

	@Override
	public void setOnTabChangedListener(OnTabChangedListener listener) {
		mOnTabChangedListener = listener;
	}

	@Override
	OnTabChangedListener getOnTabChangedListener() {
		return mOnTabChangedListener;
	}

	@Override
	public void select(boolean fromUser) {
		if (mTabBar != null) {
			mTabBar.selectTab(this, fromUser);
		}
	}

	@Override
	public Tab getInitialTab() {
		return this;
	}
}
