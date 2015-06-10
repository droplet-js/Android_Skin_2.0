package com.v7lin.style.widget;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.v7lin.style.news.R;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class StyleToast extends Toast {

	public StyleToast(Context context) {
		super(context);
	}

	@Override
	public void show() {
		updateAnim();
		super.show();
	}

	private void updateAnim() {
		// Toast 动画
		try {
			Field mTNField = Toast.class.getDeclaredField("mTN");
			if (mTNField != null) {
				mTNField.setAccessible(true);
				Object mTN = mTNField.get(this);
				if (mTN != null) {
					String mTNClazzName = Toast.class.getName() + "$TN";
					Class<?> mTNClazz = Class.forName(mTNClazzName);
					Field mParamsField = mTNClazz.getDeclaredField("mParams");
					if (mParamsField != null) {
						mParamsField.setAccessible(true);
						Object mParams = mParamsField.get(mTN);
						if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
							WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
							params.windowAnimations = R.style.Animation_Toast;
						}
					}
				}
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static StyleToast makeText(Context context, CharSequence text, int duration) {
		StyleToast result = new StyleToast(context);
		
		View contentView = View.inflate(context, R.layout.layout_toast, null);
		
		TextView message = (TextView) contentView.findViewById(R.id.message);
		message.setText(text);
		
		result.setView(contentView);
		result.setDuration(duration);
		
		return result;
	}

	public static StyleToast makeText(Context context, int resId, int duration) throws Resources.NotFoundException {
		return StyleToast.makeText(context, context.getResources().getText(resId), duration);
	}
}
