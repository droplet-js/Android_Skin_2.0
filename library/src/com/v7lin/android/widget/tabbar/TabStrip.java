package com.v7lin.android.widget.tabbar;

import android.view.View;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public interface TabStrip extends TabDivider {

	public int getTabCount();

	public Tab wrap(Tab tab);

	public void addTab(Tab tab);

	public void addTab(Tab tab, int position);

	public View removeTabAt(int position);

	public abstract void animateToTab(int position, float positionOffset);

	public void setTabSelected(int position);

}
