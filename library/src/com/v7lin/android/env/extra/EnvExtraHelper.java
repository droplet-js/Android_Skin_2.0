package com.v7lin.android.env.extra;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.v7lin.android.env.EnvResourcesManager;
import com.v7lin.android.env.EnvResourcesWrapper;
import com.v7lin.android.env.EnvSkinResourcesWrapper;
import com.v7lin.android.env.NormalEnvSetup;
import com.v7lin.android.env.font.FontFamily;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvExtraHelper {

	public static ArrayList<FontData> loadFontDatas(Activity activity, File fontDir, FontFilter fontFilter) {
		ArrayList<FontData> fontDatas = new ArrayList<FontData>();
		final Resources currRes = activity.getResources();
		if (currRes != null && currRes instanceof EnvResourcesWrapper) {
			EnvResourcesWrapper currEnvRes = (EnvResourcesWrapper) currRes;
			Resources oriRes = currEnvRes.getWrapped();
			FontFamily currFontFamily = currEnvRes.getFontFamily();

			// 可自由定制
			final int resid = android.R.string.unknownName;

			// 读取默认皮肤参数
			String defaultFontAlias = oriRes.getString(resid);

			// 读取默认字体参数
			FontFamily defaultFontFamily = FontFamily.DEFAULT_FONT;
			fontDatas.add(new FontData(defaultFontAlias, defaultFontFamily, TextUtils.equals(defaultFontFamily.getFontName(), currFontFamily.getFontName())));
			// 读取font文件夹下字体参数
			File[] fontFiles = fontDir.listFiles(fontFilter);
			if (fontFiles != null && fontFiles.length > 0) {
				for (File fontFile : fontFiles) {
					String fontPath = fontFile.getAbsolutePath();
					FontFamily fontFamily = EnvResourcesManager.getGlobal().getTopLevelFontFamily(activity, fontPath);
					fontDatas.add(new FontData(fontFamily.getFontName(), fontFamily, TextUtils.equals(fontFamily.getFontName(), currFontFamily.getFontName())));
				}
			}
		}
		return fontDatas;
	}

	protected List<SkinData> loadSkinDatas(Activity activity, File skinDir, SkinFilter skinFilter) {
		ArrayList<SkinData> skinDatas = new ArrayList<SkinData>();
		final Resources currRes = activity.getResources();
		if (currRes != null && currRes instanceof EnvResourcesWrapper) {
			Resources oriRes = ((EnvResourcesWrapper) currRes).getWrapped();
			// 可自由定制
			final int skinNameResID = android.R.string.unknownName;
			final int skinBGDrawResID = android.R.drawable.ic_menu_add;
			final int skinTextColorResID = android.R.color.black;

			final String currSkinName = currRes.getString(skinNameResID);

			// 读取默认皮肤参数
			final String defaultSkinName = oriRes.getString(skinNameResID);
			final String defaultSkinPath = null;
			final Drawable defaultSkinBGDraw = oriRes.getDrawable(skinBGDrawResID);
			final int defaultSkinTextColor = oriRes.getColor(skinTextColorResID);
			skinDatas.add(new SkinData(defaultSkinName, defaultSkinPath, defaultSkinBGDraw, defaultSkinTextColor, TextUtils.equals(defaultSkinName, currSkinName)));

			// 读取skin文件夹下皮肤参数
			File[] skinFiles = skinDir.listFiles(skinFilter);
			if (skinFiles != null && skinFiles.length > 0) {
				for (File skinFile : skinFiles) {
					final String skinPath = skinFile.getAbsolutePath();
					EnvResourcesManager manager = new EnvResourcesManager();
					manager.setEnvSetup(new NormalEnvSetup(skinPath, null));
					Context base = activity.getBaseContext();
					EnvResourcesWrapper skinRes = new EnvSkinResourcesWrapper(base, base.getResources(), manager);

					final String skinName = skinRes.getString(skinNameResID);
					final Drawable skinBGDraw = skinRes.getDrawable(skinBGDrawResID);
					final int skinTextColor = skinRes.getColor(skinTextColorResID);
					skinDatas.add(new SkinData(skinName, skinPath, skinBGDraw, skinTextColor, TextUtils.equals(skinName, currSkinName)));
				}
			}
		}

		return skinDatas;
	}

}
