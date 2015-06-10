package com.v7lin.android.widget.tabbar;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public abstract class TabBar implements TabDivider, TabConst {

	public abstract void setOnTabRecyclerListener(OnTabRecyclerListener listener);

	public abstract Tab newTab();

	public abstract void addTab(Tab tab);

	public abstract void addTab(Tab tab, int position);

	public abstract void removeTab(Tab tab, boolean fixPos);

	public abstract void removeTabAt(int position, boolean fixPos);

	public abstract void removeAllTabs();

	public abstract void animateToTab(int position, float positionOffset);

	public abstract void selectTabAt(int position, boolean fromUser);

	public abstract int getSelectedPosition();

	public abstract Tab getTabAt(int position);

	public abstract int getTabCount();

}
