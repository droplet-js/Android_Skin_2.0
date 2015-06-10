package com.v7lin.android.widget.tabbar;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class TabBarImpl extends TabBar {

	// mTabs 里可能全是 wrap 后的 Tab，也可能全是 TabImpl
	private final ArrayList<Tab> mTabs = new ArrayList<Tab>();
	// mSelectedTab 一定是 TabImpl
	private Tab mSelectedTab;

	private TabStrip mTabStrip;

	private OnTabRecyclerListener mOnTabRecyclerListener;

	public TabBarImpl(TabStrip tabStrip) {
		super();
		mTabStrip = tabStrip;
	}

	@Override
	public void setOnTabRecyclerListener(OnTabRecyclerListener listener) {
		mOnTabRecyclerListener = listener;
	}

	@Override
	public Tab newTab() {
		return new TabImpl(this);
	}

	@Override
	public void addTab(Tab tab) {
		addTab(tab, mTabs.size());
	}

	@Override
	public void addTab(Tab tab, int position) {
		if (tab != null) {
			Tab wrapper = mTabStrip.wrap(tab);
			configureTab(wrapper, position);
			mTabStrip.addTab(wrapper, position);
		}
	}

	private void configureTab(Tab tab, int position) {
		tab.setPosition(position);
		mTabs.add(position, tab);
		final int count = mTabs.size();
		for (int i = position + 1; i < count; i++) {
			mTabs.get(i).setPosition(i);
		}
	}

	@Override
	public void removeTab(Tab tab, boolean fixPos) {
		removeTabAt(tab.getPosition(), fixPos);
	}

	@Override
	public void removeTabAt(int position, boolean fixPos) {
		final int selectedTabPosition = mSelectedTab != null ? mSelectedTab.getPosition() : INVALID_POSITION;
		Tab removedTab = mTabs.remove(position);
		removedTab.setPosition(INVALID_POSITION);
		View removeTabView = mTabStrip.removeTabAt(position);
		final int newTabCount = mTabs.size();
		for (int i = position; i < newTabCount; i++) {
			mTabs.get(i).setPosition(i);
		}
		if (fixPos && selectedTabPosition == position && !removedTab.isOverflow()) {
			if (!mTabs.isEmpty()) {
				int searchIndex = position;
				Tab searchTab = null;
				if (searchIndex >= newTabCount) {
					searchIndex--;
				}
				if (mTabs.get(searchIndex).isOverflow()) {
					// 顶替项
					searchTab = mTabs.get(searchIndex);
				} else {
					// 先向下查询
					for (int i = searchIndex - 1; i >= 0; i--) {
						if (mTabs.get(i).isOverflow()) {
							searchTab = mTabs.get(i);
							break;
						}
					}
					if (searchTab == null) {
						// 再向上查询
						for (int i = searchIndex + 1; i < newTabCount; i++) {
							if (mTabs.get(i).isOverflow()) {
								searchTab = mTabs.get(i);
								break;
							}
						}
					}
				}
				selectTab(searchTab != null ? searchTab.getInitialTab() : null, true);
			} else {
				selectTab(null, true);
			}
		}
		if (mOnTabRecyclerListener != null) {
			mOnTabRecyclerListener.onTabRecycled(removedTab.getInitialTab(), removeTabView);
		}
	}

	@Override
	public void removeAllTabs() {
		ArrayList<Tab> tempTabs = new ArrayList<Tab>(mTabs);
		for (Tab tab : tempTabs) {
			removeTab(tab, false);
		}
		tempTabs.clear();
	}

	@Override
	public void setDividerDrawableSupport(Drawable divider) {
		mTabStrip.setDividerDrawableSupport(divider);
	}

	@Override
	public void setShowDividersSupport(int showDividers) {
		mTabStrip.setShowDividersSupport(showDividers);
	}

	@Override
	public void setDividerPaddingSupport(int padding) {
		mTabStrip.setDividerPaddingSupport(padding);
	}

	@Override
	public void animateToTab(int position, float positionOffset) {
		mTabStrip.animateToTab(position, positionOffset);
	}

	@Override
	public void selectTabAt(int position, boolean fromUser) {
		if (position >= 0 && position < getTabCount()) {
			getTabAt(position).select(fromUser);
		} else {
			selectTab(null, fromUser);
		}
	}

	void selectTab(Tab tab, boolean fromUser) {
		if (tab != null && tab.isOverflow()) {
			OnTabChangedListener listener = tab.getOnTabChangedListener();
			if (listener != null) {
				listener.onTabClicked(tab, fromUser);
			}
			return;
		}
		if (mSelectedTab == tab) {
			if (mSelectedTab != null) {
				OnTabChangedListener listener = mSelectedTab.getOnTabChangedListener();
				if (listener != null) {
					listener.onTabReSelected(mSelectedTab, fromUser);
				}
			}
		} else {
			mTabStrip.setTabSelected(tab != null ? tab.getPosition() : INVALID_POSITION);
			if (mSelectedTab != null) {
				OnTabChangedListener listener = mSelectedTab.getOnTabChangedListener();
				if (listener != null) {
					listener.onTabUnSelected(mSelectedTab, fromUser);
				}
			}
			mSelectedTab = tab;
			if (mSelectedTab != null) {
				OnTabChangedListener listener = mSelectedTab.getOnTabChangedListener();
				if (listener != null) {
					listener.onTabSelected(mSelectedTab, fromUser);
				}
			}
		}
	}

	@Override
	public int getSelectedPosition() {
		return mSelectedTab != null ? mSelectedTab.getPosition() : INVALID_POSITION;
	}

	@Override
	public Tab getTabAt(int position) {
		Tab tab = null;
		int tabCount = mTabs.size();
		if (position > INVALID_POSITION && position < tabCount) {
			tab = mTabs.get(position);
		}
		return tab;
	}

	@Override
	public int getTabCount() {
		return mTabs.size();
	}
}
