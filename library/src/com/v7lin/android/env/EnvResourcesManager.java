package com.v7lin.android.env;

import java.io.File;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.v7lin.android.env.font.FontFactory;
import com.v7lin.android.env.font.FontFamily;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvResourcesManager {

	private boolean mScheduleSkin = true;
	private boolean mScheduleFont = true;
	private EnvSetup mEnvSetup = NullEnvSetup.getInstance();
	private SkinChecker mSkinChecker = NormalSkinChecker.getInstance();

	public EnvResourcesManager() {
		super();
	}

	public void setEnvSetup(EnvSetup setup) {
		mEnvSetup = new EnvSetupWrapper(setup != null ? setup : NullEnvSetup.getInstance());
	}

	class EnvSetupWrapper implements EnvSetup {

		private EnvSetup mWrapped;

		public EnvSetupWrapper(EnvSetup wrapped) {
			super();
			mWrapped = wrapped;
		}

		@Override
		public String getSkinPath(Context context) {
			return mScheduleSkin ? mWrapped.getSkinPath(context) : context.getPackageResourcePath();
		}

		@Override
		public String getFontPath(Context context) {
			return mWrapped.getFontPath(context);
		}
	}

	public void setSkinChecker(SkinChecker checker) {
		mSkinChecker = checker != null ? checker : NormalSkinChecker.getInstance();
	}

	void setScheduleSkin(boolean scheduleSkin) {
		mScheduleSkin = scheduleSkin;
	}

	void setScheduleFont(boolean scheduleFont) {
		mScheduleFont = scheduleFont;
	}

	public String getSkinPath(Context context) {
		String skinPath = getRealSkinPath(context);
		if (!isSkinValid(context, skinPath)) {
			skinPath = "";
		}
		return skinPath;
	}

	public Resources getResources(Context context) {
		return getTopLevelResources(context, getRealSkinPath(context));
	}

	private String getRealSkinPath(Context context) {
		return mEnvSetup.getSkinPath(context);
	}

	private boolean isSkinValid(Context context, String skinPath) {
		return mSkinChecker.isSkinValid(context, skinPath);
	}

	public boolean isSkinChanged(Context context, String compareSkinPath) {
		String skinPath = getRealSkinPath(context);
		return !TextUtils.equals(compareSkinPath, skinPath);
	}

	public synchronized Resources getTopLevelResources(Context context, String skinPath) {
		Resources res = null;
		try {
			if (!TextUtils.isEmpty(skinPath)) {
				if (isSkinValid(context, skinPath)) {
					res = EnvResourcesCache.getInstance().getActiveResources(skinPath);
					boolean isValid = false;
					if (res != null && res.getAssets() != null) {
						AssetManager assets = res.getAssets();
						Class<?> clazz = assets.getClass();
						Method method = clazz.getDeclaredMethod("isUpToDate");
						Object object = method.invoke(assets);
						isValid = Boolean.valueOf(String.valueOf(object)).booleanValue();
					}
					if (!isValid) {
						File skinFile = new File(skinPath);
						if (skinFile.exists()) {
							Class<?> clazz = AssetManager.class;
							AssetManager skinAsset = (AssetManager) clazz.newInstance();
							Method method = clazz.getDeclaredMethod("addAssetPath", String.class);
							method.invoke(skinAsset, skinFile.getAbsolutePath());
							res = EnvResourcesHelper.newThirdResources(skinAsset, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
							EnvResourcesCache.getInstance().putActiveResources(skinPath, res);
						}
					}
				} else {
					EnvResourcesCache.getInstance().removeActiveResources(skinPath);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public String getFontPath(Context context) {
		String fontPath = getRealFontPath(context);
		if (!isFontValid(context, fontPath)) {
			fontPath = "";
		}
		return fontPath;
	}

	public FontFamily getFontFamily(Context context) {
		FontFamily fontFamily = FontFamily.DEFAULT_FONT;
		if (mScheduleFont) {
			fontFamily = getTopLevelFontFamily(context, getRealFontPath(context));
		}
		return fontFamily;
	}

	private String getRealFontPath(Context context) {
		return mEnvSetup.getFontPath(context);
	}

	private boolean isFontValid(Context context, String fontPath) {
		boolean exist = false;
		if (!TextUtils.isEmpty(fontPath)) {
			File fontFile = new File(fontPath);
			exist = fontFile.exists() && fontFile.isFile();
		}
		return exist;
	}

	public boolean isFontChanged(Context context, String compareFontPath) {
		String fontPath = getRealFontPath(context);
		return !TextUtils.equals(compareFontPath, fontPath);
	}

	public synchronized FontFamily getTopLevelFontFamily(Context context, String fontPath) {
		FontFamily fontFamily = null;
		if (!TextUtils.isEmpty(fontPath)) {
			if (isFontValid(context, fontPath)) {
				fontFamily = EnvResourcesCache.getInstance().getActiveFontFamily(fontPath);
				if (fontFamily == null) {
					fontFamily = FontFactory.parse(fontPath);
					EnvResourcesCache.getInstance().putActiveFontFamily(fontPath, fontFamily);
				}
			} else {
				EnvResourcesCache.getInstance().removeActiveFontFamily(fontPath);// 删除失效字体
			}
		}
		return fontFamily;
	}

	private static class EnvResourcesManagerHolder {
		private static final EnvResourcesManager INSTANCE = new EnvResourcesManager();
	}

	public static EnvResourcesManager getGlobal() {
		return EnvResourcesManagerHolder.INSTANCE;
	}
}
