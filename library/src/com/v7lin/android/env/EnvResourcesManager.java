package com.v7lin.android.env;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.v7lin.android.env.font.FontFactory;
import com.v7lin.android.env.font.FontFamily;
import com.v7lin.android.env.skin.SkinFactory;
import com.v7lin.android.env.skin.SkinFamily;

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
		if (!isSkinPathValid(context, skinPath)) {
			skinPath = "";
		}
		return skinPath;
	}

	public Resources getSkinRes(Context context) {
		SkinFamily skinFamily = getSkinFamily(context);
		return skinFamily != null ? skinFamily.getResources() : null;
	}

	public SkinFamily getSkinFamily(Context context) {
		return getTopLevelSkinFamily(context, getRealSkinPath(context));
	}

	private String getRealSkinPath(Context context) {
		return mEnvSetup.getSkinPath(context);
	}

	private boolean isSkinPathValid(Context context, String skinPath) {
		return mSkinChecker.isSkinValid(context, skinPath);
	}

	public boolean isSkinChanged(Context context, String compareSkinPath) {
		String skinPath = getRealSkinPath(context);
		return !TextUtils.equals(compareSkinPath, skinPath);
	}

	public synchronized SkinFamily getTopLevelSkinFamily(Context context, String skinPath) {
		SkinFamily skinFamily = null;
		if (!TextUtils.isEmpty(skinPath)) {
			if (isSkinPathValid(context, skinPath)) {
				skinFamily = EnvResourcesCache.getInstance().getActiveSkinFamily(skinPath);
				if (!SkinFactory.isValid(skinFamily)) {
					skinFamily = SkinFactory.makeSkin(context, skinPath);
					EnvResourcesCache.getInstance().putActiveSkinFamily(skinPath, skinFamily);
				}
			} else {
				EnvResourcesCache.getInstance().removeActiveSkinFamily(skinPath);
			}
		}
		return skinFamily;
	}

	public String getFontPath(Context context) {
		String fontPath = getRealFontPath(context);
		if (!isFontPathValid(context, fontPath)) {
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

	private boolean isFontPathValid(Context context, String fontPath) {
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
			if (isFontPathValid(context, fontPath)) {
				fontFamily = EnvResourcesCache.getInstance().getActiveFontFamily(fontPath);
				if (!FontFactory.isValid(fontFamily)) {
					fontFamily = FontFactory.makeFont(context, fontPath);
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
