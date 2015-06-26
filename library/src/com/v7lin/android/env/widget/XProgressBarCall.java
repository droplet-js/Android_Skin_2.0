package com.v7lin.android.env.widget;

import android.graphics.drawable.Drawable;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public interface XProgressBarCall extends XViewCall {

	public void scheduleIndeterminateDrawable(Drawable d);

	public void scheduleProgressDrawable(Drawable d);
}
