package com.v7lin.android.widget.tabbar;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public abstract class Tab implements TabConst {

	public abstract int getPosition();

	abstract void setPosition(int position);

	public abstract void setOverflow(boolean overflow);

	public abstract boolean isOverflow();

	public abstract void setTabFactory(TabFactory factory);

	public abstract TabFactory getTabFactory();

	public abstract void setOnTabChangedListener(OnTabChangedListener listener);

	abstract OnTabChangedListener getOnTabChangedListener();

	public abstract void select(boolean fromUser);

	public abstract Tab getInitialTab();

}
