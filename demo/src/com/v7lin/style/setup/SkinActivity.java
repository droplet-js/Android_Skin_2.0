package com.v7lin.style.setup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;

import com.v7lin.android.env.EnvResourcesManager;
import com.v7lin.android.env.EnvResourcesWrapper;
import com.v7lin.android.env.EnvSkinResourcesWrapper;
import com.v7lin.android.env.NormalEnvSetup;
import com.v7lin.style.app.StyleActivity;
import com.v7lin.style.news.R;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class SkinActivity extends StyleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private List<SkinData> loadSkinDatas(File skinDir, SkinFilter skinFilter) {
		ArrayList<SkinData> skinDatas = new ArrayList<SkinData>();
		final Resources currRes = getResources();
		if (currRes != null && currRes instanceof EnvResourcesWrapper) {
			Resources oriRes = ((EnvResourcesWrapper) currRes).getWrapped();
			// 可自由定制（展示给用户甄选）
			final int skinNameResID = R.string.skin_name;
			final int skinBGDrawResID = R.drawable.skin_bg;
			final int skinTextColorResID = R.color.skin_text_color;

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
					Context base = getBaseContext();
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
