package com.v7lin.android.env.widget;

import android.graphics.drawable.Drawable;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public interface XExpandableListViewCall extends XListViewCall {

	public void scheduleGroupIndicator(Drawable groupIndicator);

	public void scheduleChildIndicator(Drawable childIndicator);

	public void scheduleChildDivider(Drawable childDivider);
}
