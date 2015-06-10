package com.v7lin.android.env.view;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public final class ViewHelper {

	private ViewHelper() {
		super();
	}
	
	/**
	 * 隐藏输入法
	 * 
	 * @param context
	 * @param view
	 */
	public static void hideKeyboard(Context context, View view) {
		if (context != null && view != null) {
			InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 开启输入法
	 * 
	 * @param context
	 * @param view
	 */
	public static void showKeyboard(Context context, View view) {
		if (context != null && view != null) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			// imm.isAcceptingText();// 这个判断最好不要用，这必须是“输入法可输入”也就是“ EditText
			// 获取焦点”时才会为 true
			imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		}
	}

	/**
	 * 定位光标
	 * 
	 * @param editText
	 */
	public static void fixCursor(EditText editText) {
		if (editText != null) {
			CharSequence text = editText.getText();
			if (!TextUtils.isEmpty(text) && text instanceof Spannable) {
				Spannable span = (Spannable) text;
				Selection.setSelection(span, span.length());
			}
		}
	}

	/**
	 * 图片淡入特效
	 * 
	 * @param targetView
	 *            需要实现渐变效果的imageview
	 * @param bottomDrawable
	 *            一开始显示的背景图
	 * @param upDrawable
	 *            最终要出现的封面
	 */
	public static void transitionDrawable(ImageView targetView, Drawable bottomDrawable, Drawable upDrawable) {
		if (targetView != null && bottomDrawable != null && upDrawable != null) {
			TransitionDrawable transition = new TransitionDrawable(new Drawable[] { bottomDrawable, upDrawable });
			targetView.setImageDrawable(transition);
			transition.startTransition(300);
		}
	}

	/**
	 * ViewGroup的子 View 淡入特效
	 * 
	 * @param targetView
	 */
	public static void setLayoutAnimation(ViewGroup targetView) {
		if (targetView != null) {
			Animation anim = new AlphaAnimation(0.0f, 1.0f);
			anim.setDuration(600);

			LayoutAnimationController controller = new LayoutAnimationController(anim, 0.3f);
			controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
			targetView.setLayoutAnimation(controller);
		}
	}

	public static void setAbsListViewThumbCompat(AbsListView absListView, Drawable thumbDrawable, int thumbW, int thumbH) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			ViewHelper.setAbsListViewThumbJellyBeanMr2(absListView, thumbDrawable, thumbW, thumbH);
		} else {
			ViewHelper.setAbsListViewThumbEarly(absListView, thumbDrawable, thumbW, thumbH);
		}
	}

	/**
	 * 4.3及4.3以后的机器 Api 18 新增 ViewGroupOverlay 类，并将 AbsListView 的 FastScroller
	 * 采用 ViewGroupOverlay 实现滑块
	 */
	private static void setAbsListViewThumbJellyBeanMr2(AbsListView absListView, Drawable thumbDrawable, int thumbW, int thumbH) {
		try {
			if (absListView != null) {
				Field mFastScrollerField = AbsListView.class.getDeclaredField("mFastScroller");
				if (mFastScrollerField != null) {
					mFastScrollerField.setAccessible(true);
					Object mFastScroller = mFastScrollerField.get(absListView);

					Field mThumbImageField = mFastScrollerField.getType().getDeclaredField("mThumbImage");
					if (mThumbImageField != null) {
						mThumbImageField.setAccessible(true);
						ImageView mThumbImage = (ImageView) mThumbImageField.get(mFastScroller);
						mThumbImage.setImageDrawable(thumbDrawable);
						mThumbImage.setMinimumWidth(thumbW);
						mThumbImage.setMinimumHeight(thumbH);
						ViewGroup.LayoutParams params = mThumbImage.getLayoutParams();
						mThumbImage.setLayoutParams(params);
					}
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 4.3以前的机器
	 */
	private static void setAbsListViewThumbEarly(AbsListView absListView, Drawable thumbDrawable, int thumbW, int thumbH) {
		try {
			if (absListView != null) {
				Field mFastScrollerField = AbsListView.class.getDeclaredField("mFastScroller");
				if (mFastScrollerField != null) {
					mFastScrollerField.setAccessible(true);
					Object mFastScroller = mFastScrollerField.get(absListView);

					Field mThumbDrawableField = mFastScrollerField.getType().getDeclaredField("mThumbDrawable");
					if (mThumbDrawableField != null) {
						mThumbDrawableField.setAccessible(true);
						Drawable mThumbDrawable = (Drawable) mThumbDrawableField.get(mFastScroller);
						mThumbDrawable = thumbDrawable;
						mThumbDrawableField.set(mFastScroller, mThumbDrawable);
					}

					Field mThumbWField = mFastScrollerField.getType().getDeclaredField("mThumbW");
					if (mThumbWField != null) {
						mThumbWField.setAccessible(true);
						int mThumbW = mThumbWField.getInt(mFastScroller);
						mThumbW = thumbW;
						mThumbWField.setInt(mFastScroller, mThumbW);
					}

					Field mThumbHField = mFastScrollerField.getType().getDeclaredField("mThumbH");
					if (mThumbHField != null) {
						mThumbHField.setAccessible(true);
						int mThumbH = mThumbHField.getInt(mFastScroller);
						mThumbH = thumbH;
						mThumbHField.setInt(mFastScroller, mThumbH);
					}
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在 Activity 的 onCreate 中调用 Actionbar中在条目过多时，统一菜单风格
	 * 
	 * @param activity
	 */
	public static void fixMenuKeyStyle(Activity activity) {
		try {
			ViewConfiguration mconfig = ViewConfiguration.get(activity);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
