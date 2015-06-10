package com.v7lin.android.widget.tabbar;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public interface OnTabChangedListener {

	public void onTabSelected(Tab tab, boolean fromUser);

	public void onTabUnSelected(Tab tab, boolean fromUser);

	public void onTabReSelected(Tab tab, boolean fromUser);

	public void onTabClicked(Tab tab, boolean fromUser);

}
