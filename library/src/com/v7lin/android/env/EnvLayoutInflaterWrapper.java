package com.v7lin.android.env;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.v7lin.android.BuildConfig;
import com.v7lin.android.env.webkit.CompatWebView;
import com.v7lin.android.env.widget.CompatEditText;
import com.v7lin.android.env.widget.CompatFrameLayout;
import com.v7lin.android.env.widget.CompatImageView;
import com.v7lin.android.env.widget.CompatLinearLayout;
import com.v7lin.android.env.widget.CompatListView;
import com.v7lin.android.env.widget.CompatProgressBar;
import com.v7lin.android.env.widget.CompatRelativeLayout;
import com.v7lin.android.env.widget.CompatSeekBar;
import com.v7lin.android.env.widget.CompatSpinner;
import com.v7lin.android.env.widget.CompatTextView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvLayoutInflaterWrapper extends LayoutInflaterWrapper {

	private static final String[] sClassPrefixArray = {
			// widget
			"android.widget.",
			// webkit
			"android.webkit.",
			// view
			"android.view.",
			// app
			"android.app." };

	private static final List<String> sClassPrefixList = Arrays.asList(sClassPrefixArray);

	public EnvLayoutInflaterWrapper(LayoutInflater original, Context newContext) {
		super(original, newContext);

		setup();
	}

	private void setup() {
		Factory factory = getFactory();
		if (!(factory instanceof EnvFactory)) {
			setFactory(new EnvFactory(factory));
		}
	}

	@Override
	public LayoutInflater cloneInContext(Context newContext) {
		return new EnvLayoutInflaterWrapper(this, newContext);
	}

	/**
	 * 替换掉某些 SDK 中有 BUG 的 View 或 Widget 或者 替换为某些特殊定制的 View 或
	 * Widget（兼容高版本的一些特性或时下流行元素等）
	 */
	protected String transfer(String name) {
		String transferName = name;
		if (TextUtils.equals(name, TextView.class.getSimpleName())) {
			transferName = CompatTextView.class.getName();
		} else if (TextUtils.equals(name, EditText.class.getSimpleName())) {
			transferName = CompatEditText.class.getName();
		} else if (TextUtils.equals(name, ProgressBar.class.getSimpleName())) {
			transferName = CompatProgressBar.class.getName();
		} else if (TextUtils.equals(name, SeekBar.class.getSimpleName())) {
			transferName = CompatSeekBar.class.getName();
		} else if (TextUtils.equals(name, ImageView.class.getSimpleName())) {
			transferName = CompatImageView.class.getName();
		} else if (TextUtils.equals(name, LinearLayout.class.getSimpleName())) {
			transferName = CompatLinearLayout.class.getName();
		} else if (TextUtils.equals(name, FrameLayout.class.getSimpleName())) {
			transferName = CompatFrameLayout.class.getName();
		} else if (TextUtils.equals(name, RelativeLayout.class.getSimpleName())) {
			transferName = CompatRelativeLayout.class.getName();
		} else if (TextUtils.equals(name, ListView.class.getSimpleName())) {
			transferName = CompatListView.class.getName();
		} else if (TextUtils.equals(name, Spinner.class.getSimpleName())) {
			transferName = CompatSpinner.class.getName();
		} else if (TextUtils.equals(name, WebView.class.getSimpleName())) {
			transferName = CompatWebView.class.getName();
		}
		return transferName;
	}

	class EnvFactory implements Factory {

		private final LayoutInflater.Factory wrapped;

		public EnvFactory(Factory wrapped) {
			super();
			this.wrapped = wrapped;
		}

		@Override
		public View onCreateView(String name, Context context, AttributeSet attrs) {
			View view = null;
			name = transfer(name);
			if (context instanceof LayoutInflater.Factory) {
				view = ((LayoutInflater.Factory) context).onCreateView(name, context, attrs);
			}
			if (view == null && wrapped != null) {
				view = wrapped.onCreateView(name, context, attrs);
			}
			if (view == null) {
				view = createViewOrFailQuietly(name, context, attrs);
			}
			return view;
		}

		private View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
			if (name.contains(".")) {
				return createViewOrFailQuietly(name, null, context, attrs);
			}
			for (final String prefix : sClassPrefixList) {
				final View view = createViewOrFailQuietly(name, prefix, context, attrs);
				if (view != null) {
					return view;
				}
			}
			return null;
		}

		private View createViewOrFailQuietly(String name, String prefix, Context context, AttributeSet attrs) {
			try {
				return LayoutInflater.from(context).createView(name, prefix, attrs);
			} catch (Exception ignore) {
				if (BuildConfig.DEBUG) {
					ignore.printStackTrace();
				}
				return null;
			}
		}
	}
}
