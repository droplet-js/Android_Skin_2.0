package com.v7lin.android.env;

import java.util.HashMap;

import android.view.View;
import android.webkit.WebView;
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
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

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
class InternalViewMap implements EnvViewMap {

	private static final HashMap<String, String> VIEW_CLASS_MAP = new HashMap<String, String>();

	static {
		VIEW_CLASS_MAP.put(AutoCompleteTextView.class.getName(), CompatAutoCompleteTextView.class.getName());
		VIEW_CLASS_MAP.put(Button.class.getName(), CompatButton.class.getName());
		VIEW_CLASS_MAP.put(CheckBox.class.getName(), CompatCheckBox.class.getName());
		VIEW_CLASS_MAP.put(Chronometer.class.getName(), CompatChronometer.class.getName());
		VIEW_CLASS_MAP.put(CheckedTextView.class.getName(), CompatCheckedTextView.class.getName());
		VIEW_CLASS_MAP.put(DatePicker.class.getName(), CompatDatePicker.class.getName());
		VIEW_CLASS_MAP.put(EditText.class.getName(), CompatEditText.class.getName());
		VIEW_CLASS_MAP.put(ExpandableListView.class.getName(), CompatExpandableListView.class.getName());
		VIEW_CLASS_MAP.put(FrameLayout.class.getName(), CompatFrameLayout.class.getName());
		VIEW_CLASS_MAP.put(GridView.class.getName(), CompatGridView.class.getName());
		VIEW_CLASS_MAP.put(HorizontalScrollView.class.getName(), CompatHorizontalScrollView.class.getName());
		VIEW_CLASS_MAP.put(ImageButton.class.getName(), CompatImageButton.class.getName());
		VIEW_CLASS_MAP.put(ImageSwitcher.class.getName(), CompatImageSwitcher.class.getName());
		VIEW_CLASS_MAP.put(ImageView.class.getName(), CompatImageView.class.getName());
		VIEW_CLASS_MAP.put(LinearLayout.class.getName(), CompatLinearLayout.class.getName());
		VIEW_CLASS_MAP.put(ListView.class.getName(), CompatListView.class.getName());
		VIEW_CLASS_MAP.put(ProgressBar.class.getName(), CompatProgressBar.class.getName());
		VIEW_CLASS_MAP.put(RadioButton.class.getName(), CompatRadioButton.class.getName());
		VIEW_CLASS_MAP.put(RatingBar.class.getName(), CompatRatingBar.class.getName());
		VIEW_CLASS_MAP.put(RelativeLayout.class.getName(), CompatRelativeLayout.class.getName());
		VIEW_CLASS_MAP.put(ScrollView.class.getName(), CompatScrollView.class.getName());
		VIEW_CLASS_MAP.put(SeekBar.class.getName(), CompatSeekBar.class.getName());
		VIEW_CLASS_MAP.put(Spinner.class.getName(), CompatSpinner.class.getName());
		VIEW_CLASS_MAP.put(TextSwitcher.class.getName(), CompatTextSwitcher.class.getName());
		VIEW_CLASS_MAP.put(TextView.class.getName(), CompatTextView.class.getName());
		VIEW_CLASS_MAP.put(TimePicker.class.getName(), CompatTimePicker.class.getName());
		VIEW_CLASS_MAP.put(ToggleButton.class.getName(), CompatToggleButton.class.getName());
		VIEW_CLASS_MAP.put(View.class.getName(), CompatView.class.getName());
		VIEW_CLASS_MAP.put(ViewAnimator.class.getName(), CompatViewAnimator.class.getName());
		VIEW_CLASS_MAP.put(ViewFlipper.class.getName(), CompatViewFlipper.class.getName());
		VIEW_CLASS_MAP.put(ViewSwitcher.class.getName(), CompatViewSwitcher.class.getName());
		VIEW_CLASS_MAP.put(WebView.class.getName(), CompatWebView.class.getName());
	}

	private InternalViewMap() {
		super();
	}

	@Override
	public String transfer(String name) {
		return VIEW_CLASS_MAP.get(name);
	}
	
	private static class InternalViewMapHolder {
		private static final InternalViewMap INSTANCE = new InternalViewMap();
	}
	
	public static InternalViewMap getInstance() {
		return InternalViewMapHolder.INSTANCE;
	}
}
