package com.v7lin.android.widget.tabbar;

import android.graphics.drawable.Drawable;

import com.v7lin.android.env.widget.CompatLinearLayout;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public interface TabDivider {

	public static final int TAB_SHOW_DIVIDER_NONE = CompatLinearLayout.SHOW_DIVIDER_NONE_SUPPORT;
	public static final int TAB_SHOW_DIVIDER_BEGINNING = CompatLinearLayout.SHOW_DIVIDER_BEGINNING_SUPPORT;
	public static final int TAB_SHOW_DIVIDER_MIDDLE = CompatLinearLayout.SHOW_DIVIDER_MIDDLE_SUPPORT;
	public static final int TAB_SHOW_DIVIDER_END = CompatLinearLayout.SHOW_DIVIDER_END_SUPPORT;

	public void setDividerDrawableSupport(Drawable divider);

	public void setShowDividersSupport(int showDividers);

	public void setDividerPaddingSupport(int padding);

}
