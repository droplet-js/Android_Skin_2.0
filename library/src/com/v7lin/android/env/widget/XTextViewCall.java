package com.v7lin.android.env.widget;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public interface XTextViewCall extends XViewCall {

	public void scheduleHighlightColor(int color);

	public void scheduleTextColor(ColorStateList colors);

	public void scheduleHintTextColor(ColorStateList colors);

	public void scheduleLinkTextColor(ColorStateList colors);

	public void scheduleCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom);
}
