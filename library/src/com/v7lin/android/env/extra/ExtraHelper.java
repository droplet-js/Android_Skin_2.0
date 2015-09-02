package com.v7lin.android.env.extra;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

import com.v7lin.android.env.EnvResourcesManager;
import com.v7lin.android.env.EnvResourcesWrapper;
import com.v7lin.android.env.font.FontFamily;
import com.v7lin.android.env.skin.SkinFamily;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class ExtraHelper {

	public static <FontData> ArrayList<FontData> loadFontDatas(Context context, File fontDir, FilenameFilter fontFilter, ExtraCreator<FontData, FontFamily> creator) {
		ArrayList<FontData> fontDatas = new ArrayList<FontData>();
		final Resources currRes = context.getResources();
		if (currRes != null && currRes instanceof EnvResourcesWrapper) {
			EnvResourcesWrapper currEnvRes = (EnvResourcesWrapper) currRes;
			Resources oriRes = currEnvRes.getWrapped();
			
			// 当前字体
			FontFamily currFontFamily = currEnvRes.getFontFamily();

			// 读取默认字体参数
			FontFamily defaultFontFamily = FontFamily.DEFAULT_FONT;
			fontDatas.add(creator.createFrom(oriRes, defaultFontFamily, currFontFamily));
			
			// 读取font文件夹下字体参数
			File[] fontFiles = fontDir.listFiles(fontFilter);
			if (fontFiles != null && fontFiles.length > 0) {
				for (File fontFile : fontFiles) {
					String fontPath = fontFile.getAbsolutePath();
					FontFamily fontFamily = EnvResourcesManager.getGlobal().getTopLevelFontFamily(context, fontPath);
					fontDatas.add(creator.createFrom(oriRes, fontFamily, currFontFamily));
				}
			}
		}
		return fontDatas;
	}

	public static <SkinData> List<SkinData> loadSkinDatas(Context context, File skinDir, FilenameFilter skinFilter, ExtraCreator<SkinData, SkinFamily> creator) {
		ArrayList<SkinData> skinDatas = new ArrayList<SkinData>();
		final Resources currRes = context.getResources();
		if (currRes != null && currRes instanceof EnvResourcesWrapper) {
			EnvResourcesWrapper currEnvRes = (EnvResourcesWrapper) currRes;
			Resources oriRes = currEnvRes.getWrapped();
			
			// 当前皮肤
			SkinFamily currSkinFamily = currEnvRes.getSkinFamily();
			
			// 读取默认皮肤参数
			SkinFamily defaultSkinFamily = new SkinFamily(null, context.getPackageName(), oriRes);
			skinDatas.add(creator.createFrom(oriRes, defaultSkinFamily, currSkinFamily));

			// 读取skin文件夹下皮肤参数
			File[] skinFiles = skinDir.listFiles(skinFilter);
			if (skinFiles != null && skinFiles.length > 0) {
				for (File skinFile : skinFiles) {
					String skinPath = skinFile.getAbsolutePath();
					SkinFamily skinFamily = EnvResourcesManager.getGlobal().getTopLevelSkinFamily(context, skinPath);
					skinDatas.add(creator.createFrom(oriRes, skinFamily, currSkinFamily));
				}
			}
		}

		return skinDatas;
	}

}
