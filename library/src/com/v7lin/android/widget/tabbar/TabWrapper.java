package com.v7lin.android.widget.tabbar;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class TabWrapper extends Tab {

	private Tab mWrapped;

	public TabWrapper(Tab wrapped) {
		super();
		this.mWrapped = wrapped;
	}

	@Override
	void setPosition(int position) {
		mWrapped.setPosition(position);
	}

	@Override
	public int getPosition() {
		return mWrapped.getPosition();
	}

	@Override
	public void setOverflow(boolean overflow) {
		mWrapped.setOverflow(overflow);
	}

	@Override
	public boolean isOverflow() {
		return mWrapped.isOverflow();
	}

	@Override
	public void setTabFactory(TabFactory factory) {
		mWrapped.setTabFactory(factory);
	}

	@Override
	public TabFactory getTabFactory() {
		return mWrapped.getTabFactory();
	}

	@Override
	public void setOnTabChangedListener(OnTabChangedListener listener) {
		mWrapped.setOnTabChangedListener(listener);
	}

	@Override
	OnTabChangedListener getOnTabChangedListener() {
		return mWrapped.getOnTabChangedListener();
	}

	@Override
	public void select(boolean fromUser) {
		mWrapped.select(fromUser);
	}

	@Override
	public Tab getInitialTab() {
		return mWrapped.getInitialTab();
	}
}
