package com.v7lin.android.env;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.v7lin.android.BuildConfig;
import com.v7lin.android.env.webkit.CompatWebView;
import com.v7lin.android.env.widget.CompatAutoCompleteTextView;
import com.v7lin.android.env.widget.CompatButton;
import com.v7lin.android.env.widget.CompatCheckBox;
import com.v7lin.android.env.widget.CompatCheckedTextView;
import com.v7lin.android.env.widget.CompatChronometer;
import com.v7lin.android.env.widget.CompatDatePicker;
import com.v7lin.android.env.widget.CompatEditText;
import com.v7lin.android.env.widget.CompatExpandableListView;
import com.v7lin.android.env.widget.CompatFrameLayout;
import com.v7lin.android.env.widget.CompatGridView;
import com.v7lin.android.env.widget.CompatHorizontalScrollView;
import com.v7lin.android.env.widget.CompatImageButton;
import com.v7lin.android.env.widget.CompatImageSwitcher;
import com.v7lin.android.env.widget.CompatImageView;
import com.v7lin.android.env.widget.CompatLinearLayout;
import com.v7lin.android.env.widget.CompatListView;
import com.v7lin.android.env.widget.CompatProgressBar;
import com.v7lin.android.env.widget.CompatRadioButton;
import com.v7lin.android.env.widget.CompatRatingBar;
import com.v7lin.android.env.widget.CompatRelativeLayout;
import com.v7lin.android.env.widget.CompatScrollView;
import com.v7lin.android.env.widget.CompatSeekBar;
import com.v7lin.android.env.widget.CompatSpinner;
import com.v7lin.android.env.widget.CompatTextClock;
import com.v7lin.android.env.widget.CompatTextSwitcher;
import com.v7lin.android.env.widget.CompatTextView;
import com.v7lin.android.env.widget.CompatTimePicker;
import com.v7lin.android.env.widget.CompatToggleButton;
import com.v7lin.android.env.widget.CompatView;
import com.v7lin.android.env.widget.CompatViewAnimator;
import com.v7lin.android.env.widget.CompatViewFlipper;
import com.v7lin.android.env.widget.CompatViewSwitcher;

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
	
	private static final HashMap<String, String> VIEW_CLASS_MAP = new HashMap<String, String>();

	static {
		VIEW_CLASS_MAP.put(AutoCompleteTextView.class.getSimpleName(), CompatAutoCompleteTextView.class.getName());
		VIEW_CLASS_MAP.put(Button.class.getSimpleName(), CompatButton.class.getName());
		VIEW_CLASS_MAP.put(CheckBox.class.getSimpleName(), CompatCheckBox.class.getName());
		VIEW_CLASS_MAP.put(Chronometer.class.getSimpleName(), CompatChronometer.class.getName());
		VIEW_CLASS_MAP.put(CheckedTextView.class.getSimpleName(), CompatCheckedTextView.class.getName());
		VIEW_CLASS_MAP.put(DatePicker.class.getSimpleName(), CompatDatePicker.class.getName());
		VIEW_CLASS_MAP.put(EditText.class.getSimpleName(), CompatEditText.class.getName());
		VIEW_CLASS_MAP.put(ExpandableListView.class.getSimpleName(), CompatExpandableListView.class.getName());
		VIEW_CLASS_MAP.put(FrameLayout.class.getSimpleName(), CompatFrameLayout.class.getName());
		VIEW_CLASS_MAP.put(GridView.class.getSimpleName(), CompatGridView.class.getName());
		VIEW_CLASS_MAP.put(HorizontalScrollView.class.getSimpleName(), CompatHorizontalScrollView.class.getName());
		VIEW_CLASS_MAP.put(ImageButton.class.getSimpleName(), CompatImageButton.class.getName());
		VIEW_CLASS_MAP.put(ImageSwitcher.class.getSimpleName(), CompatImageSwitcher.class.getName());
		VIEW_CLASS_MAP.put(ImageView.class.getSimpleName(), CompatImageView.class.getName());
		VIEW_CLASS_MAP.put(LinearLayout.class.getSimpleName(), CompatLinearLayout.class.getName());
		VIEW_CLASS_MAP.put(ListView.class.getSimpleName(), CompatListView.class.getName());
		VIEW_CLASS_MAP.put(ProgressBar.class.getSimpleName(), CompatProgressBar.class.getName());
		VIEW_CLASS_MAP.put(RadioButton.class.getSimpleName(), CompatRadioButton.class.getName());
		VIEW_CLASS_MAP.put(RatingBar.class.getSimpleName(), CompatRatingBar.class.getName());
		VIEW_CLASS_MAP.put(RelativeLayout.class.getSimpleName(), CompatRelativeLayout.class.getName());
		VIEW_CLASS_MAP.put(ScrollView.class.getSimpleName(), CompatScrollView.class.getName());
		VIEW_CLASS_MAP.put(SeekBar.class.getSimpleName(), CompatSeekBar.class.getName());
		VIEW_CLASS_MAP.put(Spinner.class.getSimpleName(), CompatSpinner.class.getName());
		VIEW_CLASS_MAP.put(TextClock.class.getSimpleName(), CompatTextClock.class.getName());
		VIEW_CLASS_MAP.put(TextSwitcher.class.getSimpleName(), CompatTextSwitcher.class.getName());
		VIEW_CLASS_MAP.put(TextView.class.getSimpleName(), CompatTextView.class.getName());
		VIEW_CLASS_MAP.put(TimePicker.class.getSimpleName(), CompatTimePicker.class.getName());
		VIEW_CLASS_MAP.put(ToggleButton.class.getSimpleName(), CompatToggleButton.class.getName());
		VIEW_CLASS_MAP.put(View.class.getSimpleName(), CompatView.class.getName());
		VIEW_CLASS_MAP.put(ViewAnimator.class.getSimpleName(), CompatViewAnimator.class.getName());
		VIEW_CLASS_MAP.put(ViewFlipper.class.getSimpleName(), CompatViewFlipper.class.getName());
		VIEW_CLASS_MAP.put(ViewSwitcher.class.getSimpleName(), CompatViewSwitcher.class.getName());
	}

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
		String transferName = VIEW_CLASS_MAP.get(name);
		if (TextUtils.isEmpty(transferName)) {
			transferName = name;
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
