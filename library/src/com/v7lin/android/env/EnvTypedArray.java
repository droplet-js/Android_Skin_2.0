package com.v7lin.android.env;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvTypedArray {

	private final Context mContext;
	private final TypedArray mWrapped;
	private final String mPackageName;

	private EnvTypedArray(Context context, TypedArray wrapped) {
		super();
		mContext = context;
		mWrapped = wrapped;
		mPackageName = context.getPackageName();
	}

	public EnvRes getEnvRes(int index, boolean allowSysRes) {
		return getEnvRes(index, null, allowSysRes);
	}
	
	public EnvRes getEnvRes(int index, EnvRes defEnvRes, boolean allowSysRes) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				String packageName = mContext.getResources().getResourcePackageName(res.getResid());
				// MIUI上替换EditText的菜单Drawable会出问题，故而这里将替换系统Drawable资源排除
				if (allowSysRes || TextUtils.equals(packageName, mPackageName)) {
					return res;
				}
			}
		}
		return defEnvRes;
	}

	public Drawable getDrawable(int index) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getDrawable(res.getResid());
			}
		}
		return mWrapped.getDrawable(index);
	}

	public CharSequence getText(int index) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getText(res.getResid());
			}
		}
		return mWrapped.getText(index);
	}

	public String getString(int index) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getString(res.getResid());
			}
		}
		return mWrapped.getString(index);
	}

	public int getColor(int index, int defValue) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getColor(res.getResid());
			}
		}
		return mWrapped.getColor(index, defValue);
	}

	public ColorStateList getColorStateList(int index) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getColorStateList(res.getResid());
			}
		}
		return mWrapped.getColorStateList(index);
	}

	public int getInteger(int index, int defValue) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getInteger(res.getResid());
			}
		}
		return mWrapped.getInteger(index, defValue);
	}

	public float getDimension(int index, float defValue) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getDimension(res.getResid());
			}
		}
		return mWrapped.getDimension(index, defValue);
	}

	public int getDimensionPixelOffset(int index, int defValue) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getDimensionPixelOffset(res.getResid());
			}
		}
		return mWrapped.getDimensionPixelOffset(index, defValue);
	}

	public int getDimensionPixelSize(int index, int defValue) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getDimensionPixelSize(res.getResid());
			}
		}
		return mWrapped.getDimensionPixelSize(index, defValue);
	}

	public float getFraction(int index, int base, int pbase, float defValue) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getFraction(res.getResid(), base, pbase);
			}
		}
		return mWrapped.getFraction(index, base, pbase, defValue);
	}

	public CharSequence[] getTextArray(int index) {
		if (mWrapped.hasValue(index)) {
			EnvRes res = new EnvRes(mWrapped.getResourceId(index, 0));
			if (res.isValid()) {
				return mContext.getResources().getTextArray(res.getResid());
			}
		}
		return mWrapped.getTextArray(index);
	}

	public String getNonResourceString(int index) {
		return mWrapped.getNonResourceString(index);
	}

	public boolean getBoolean(int index, boolean defValue) {
		return mWrapped.getBoolean(index, defValue);
	}

	public int getInt(int index, int defValue) {
		return mWrapped.getInt(index, defValue);
	}

	public float getFloat(int index, float defValue) {
		return mWrapped.getFloat(index, defValue);
	}

	public int getLayoutDimension(int index, String name) {
		return mWrapped.getLayoutDimension(index, name);
	}

	public int getLayoutDimension(int index, int defValue) {
		return mWrapped.getLayoutDimension(index, defValue);
	}

	public int length() {
		return mWrapped.length();
	}

	public int getIndexCount() {
		return mWrapped.getIndexCount();
	}

	public int getIndex(int at) {
		return mWrapped.getIndex(at);
	}

	public int getResourceId(int index, int defValue) {
		return mWrapped.getResourceId(index, defValue);
	}

	public boolean getValue(int index, TypedValue outValue) {
		return mWrapped.getValue(index, outValue);
	}

	public boolean hasValue(int index) {
		return mWrapped.hasValue(index);
	}

	public TypedValue peekValue(int index) {
		return mWrapped.peekValue(index);
	}

	public String getPositionDescription() {
		return mWrapped.getPositionDescription();
	}

	public void recycle() {
		mWrapped.recycle();
	}
	
	public static EnvTypedArray obtainStyledAttributes(Context context, int[] attrs) {
		TypedArray wrapped = context.obtainStyledAttributes(attrs);
        return new EnvTypedArray(context, wrapped);
    }
	
	public static EnvTypedArray obtainStyledAttributes(Context context, int resid, int[] attrs) {
		TypedArray wrapped = context.obtainStyledAttributes(resid, attrs);
		return new EnvTypedArray(context, wrapped);
	}

	public static EnvTypedArray obtainStyledAttributes(Context context, AttributeSet set, int[] attrs) {
		TypedArray wrapped = context.obtainStyledAttributes(set, attrs);
		return new EnvTypedArray(context, wrapped);
	}

	public static EnvTypedArray obtainStyledAttributes(Context context, AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) {
		TypedArray wrapped = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
		return new EnvTypedArray(context, wrapped);
	}
}
