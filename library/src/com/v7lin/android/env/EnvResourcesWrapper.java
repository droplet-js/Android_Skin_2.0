package com.v7lin.android.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.v7lin.android.env.font.FontFamily;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class EnvResourcesWrapper extends Resources {

	private final Context mContext;
	private final Resources mWrapped;
	private final EnvResourcesManager mResourcesManager;

	public EnvResourcesWrapper(Context context, Resources res, EnvResourcesManager manager) {
		super(res.getAssets(), res.getDisplayMetrics(), res.getConfiguration());
		checkSystemConfig(this);
		checkSystemConfig(res);

		mContext = context;
		mWrapped = res;
		mResourcesManager = manager;
	}

	private void checkSystemConfig(Resources res) {
		// 设置字体大小不随系统设置而改变
		Configuration defConfig = new Configuration();
		Configuration config = res.getConfiguration();
		config.fontScale = defConfig.fontScale;

		// 默认语言
		config.locale = Locale.SIMPLIFIED_CHINESE;

		// 暂未作处理
		// 有些机器（如：三星 N7100）屏幕达到 xhdpi 标准，却还是 hdpi，故而在此做一次校正
		DisplayMetrics metrics = new DisplayMetrics();
		metrics.setTo(res.getDisplayMetrics());

		res.updateConfiguration(config, metrics);
	}

	public Resources getWrapped() {
		return mWrapped;
	}

	public FontFamily getFontFamily() {
		FontFamily fontFamily = mResourcesManager.getFontFamily(mContext);
		if (fontFamily == null) {
			if (mWrapped instanceof EnvResourcesWrapper) {
				fontFamily = ((EnvResourcesWrapper) mWrapped).getFontFamily();
			}
		}
		if (fontFamily == null) {
			fontFamily = FontFamily.DEFAULT_FONT;
		}
		return fontFamily;
	}

	@Override
	public CharSequence getText(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getText(id) : super.getText(id);
	}

	@Override
	public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
		return mWrapped != null ? mWrapped.getQuantityText(id, quantity) : super.getQuantityText(id, quantity);
	}

	@Override
	public String getString(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getString(id) : super.getString(id);
	}

	@Override
	public String getString(int id, Object... formatArgs) throws NotFoundException {
		return mWrapped != null ? mWrapped.getString(id, formatArgs) : super.getString(id);
	}

	@Override
	public String getQuantityString(int id, int quantity, Object... formatArgs) throws NotFoundException {
		return mWrapped != null ? mWrapped.getQuantityString(id, quantity, formatArgs) : super.getQuantityString(id, quantity, formatArgs);
	}

	@Override
	public String getQuantityString(int id, int quantity) throws NotFoundException {
		return mWrapped != null ? mWrapped.getQuantityString(id, quantity) : super.getQuantityString(id, quantity);
	}

	@Override
	public CharSequence getText(int id, CharSequence def) {
		return mWrapped != null ? mWrapped.getText(id, def) : super.getText(id, def);
	}

	@Override
	public CharSequence[] getTextArray(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getTextArray(id) : super.getTextArray(id);
	}

	@Override
	public String[] getStringArray(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getStringArray(id) : super.getStringArray(id);
	}

	@Override
	public int[] getIntArray(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getIntArray(id) : super.getIntArray(id);
	}

	@Override
	public TypedArray obtainTypedArray(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.obtainTypedArray(id) : super.obtainTypedArray(id);
	}

	@Override
	public float getDimension(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getDimension(id) : super.getDimension(id);
	}

	@Override
	public int getDimensionPixelOffset(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getDimensionPixelOffset(id) : super.getDimensionPixelOffset(id);
	}

	@Override
	public int getDimensionPixelSize(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getDimensionPixelSize(id) : super.getDimensionPixelSize(id);
	}

	@Override
	public float getFraction(int id, int base, int pbase) {
		return mWrapped != null ? mWrapped.getFraction(id, base, pbase) : super.getFraction(id, base, pbase);
	}

	@Override
	public Drawable getDrawable(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getDrawable(id) : super.getDrawable(id);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	@Override
	public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
		return mWrapped != null ? mWrapped.getDrawableForDensity(id, density) : super.getDrawableForDensity(id, density);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public Drawable getDrawable(int id, Theme theme) throws NotFoundException {
		return mWrapped != null ? mWrapped.getDrawable(id, theme) : super.getDrawable(id, theme);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public Drawable getDrawableForDensity(int id, int density, Theme theme) {
		return mWrapped != null ? mWrapped.getDrawableForDensity(id, density, theme) : super.getDrawableForDensity(id, density, theme);
	}

	@Override
	public Movie getMovie(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getMovie(id) : super.getMovie(id);
	}

	@Override
	public int getColor(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getColor(id) : super.getColor(id);
	}

	@Override
	public ColorStateList getColorStateList(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getColorStateList(id) : super.getColorStateList(id);
	}

	@Override
	public boolean getBoolean(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getBoolean(id) : super.getBoolean(id);
	}

	@Override
	public int getInteger(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getInteger(id) : super.getInteger(id);
	}

	@Override
	public XmlResourceParser getLayout(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getLayout(id) : super.getLayout(id);
	}

	@Override
	public XmlResourceParser getAnimation(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getAnimation(id) : super.getAnimation(id);
	}

	@Override
	public XmlResourceParser getXml(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.getXml(id) : super.getXml(id);
	}

	@Override
	public InputStream openRawResource(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.openRawResource(id) : super.openRawResource(id);
	}

	@Override
	public InputStream openRawResource(int id, TypedValue value) throws NotFoundException {
		return mWrapped != null ? mWrapped.openRawResource(id, value) : super.openRawResource(id, value);
	}

	@Override
	public AssetFileDescriptor openRawResourceFd(int id) throws NotFoundException {
		return mWrapped != null ? mWrapped.openRawResourceFd(id) : super.openRawResourceFd(id);
	}

	@Override
	public void getValue(int id, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
		if (mWrapped != null) {
			mWrapped.getValue(id, outValue, resolveRefs);
		} else {
			super.getValue(id, outValue, resolveRefs);
		}
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	@Override
	public void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
		if (mWrapped != null) {
			mWrapped.getValueForDensity(id, density, outValue, resolveRefs);
		} else {
			super.getValueForDensity(id, density, outValue, resolveRefs);
		}
	}

	@Override
	public void getValue(String name, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
		if (mWrapped != null) {
			mWrapped.getValue(name, outValue, resolveRefs);
		} else {
			super.getValue(name, outValue, resolveRefs);
		}
	}

	@Override
	public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
		return mWrapped != null ? mWrapped.obtainAttributes(set, attrs) : super.obtainAttributes(set, attrs);
	}

	@Override
	public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
		if (mWrapped != null) {
			mWrapped.updateConfiguration(config, metrics);
		} else {
			super.updateConfiguration(config, metrics);
		}
	}

	@Override
	public DisplayMetrics getDisplayMetrics() {
		return mWrapped != null ? mWrapped.getDisplayMetrics() : super.getDisplayMetrics();
	}

	@Override
	public Configuration getConfiguration() {
		return mWrapped != null ? mWrapped.getConfiguration() : super.getConfiguration();
	}

	@Override
	public int getIdentifier(String name, String defType, String defPackage) {
		return mWrapped != null ? mWrapped.getIdentifier(name, defType, defPackage) : super.getIdentifier(name, defType, defPackage);
	}

	@Override
	public String getResourceName(int resid) throws NotFoundException {
		return mWrapped != null ? mWrapped.getResourceName(resid) : super.getResourceName(resid);
	}

	@Override
	public String getResourcePackageName(int resid) throws NotFoundException {
		return mWrapped != null ? mWrapped.getResourcePackageName(resid) : super.getResourcePackageName(resid);
	}

	@Override
	public String getResourceTypeName(int resid) throws NotFoundException {
		return mWrapped != null ? mWrapped.getResourceTypeName(resid) : super.getResourceTypeName(resid);
	}

	@Override
	public String getResourceEntryName(int resid) throws NotFoundException {
		return mWrapped != null ? mWrapped.getResourceEntryName(resid) : super.getResourceEntryName(resid);
	}

	@Override
	public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle) throws XmlPullParserException, IOException {
		if (mWrapped != null) {
			mWrapped.parseBundleExtras(parser, outBundle);
		} else {
			super.parseBundleExtras(parser, outBundle);
		}
	}

	@Override
	public void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle) throws XmlPullParserException {
		if (mWrapped != null) {
			mWrapped.parseBundleExtra(tagName, attrs, outBundle);
		} else {
			super.parseBundleExtra(tagName, attrs, outBundle);
		}
	}
}
