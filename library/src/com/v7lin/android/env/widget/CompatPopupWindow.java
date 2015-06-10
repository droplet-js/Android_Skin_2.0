package com.v7lin.android.env.widget;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.PopupWindow;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatPopupWindow extends PopupWindow {

	private static final String TAG = "CompatPopupWindow";

	public CompatPopupWindow(Context context) {
		this(context, null);
	}

	public CompatPopupWindow(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.popupWindowStyle);
	}

	public CompatPopupWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			// For devices pre-ICS, we need to wrap the internal
			// OnScrollChangedListener
			// due to NPEs.
			wrapOnScrollChangedListener(this);
		}
	}

	@SuppressWarnings("unchecked")
	private static void wrapOnScrollChangedListener(final PopupWindow popup) {
		try {
			final Field fieldAnchor = PopupWindow.class.getDeclaredField("mAnchor");
			fieldAnchor.setAccessible(true);

			final Field fieldListener = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
			fieldListener.setAccessible(true);

			final OnScrollChangedListener originalListener = (OnScrollChangedListener) fieldListener.get(popup);

			// Now set a new listener, wrapping the original and only proxying
			// the call when
			// we have an anchor view.
			fieldListener.set(popup, new OnScrollChangedListener() {
				@Override
				public void onScrollChanged() {
					try {
						WeakReference<View> mAnchor = (WeakReference<View>) fieldAnchor.get(popup);
						if (mAnchor == null || mAnchor.get() == null) {
							return;
						} else {
							originalListener.onScrollChanged();
						}
					} catch (IllegalAccessException e) {
						// Oh well...
					}
				}
			});
		} catch (Exception e) {
			Log.d(TAG, "Exception while installing workaround OnScrollChangedListener", e);
		}
	}
}
