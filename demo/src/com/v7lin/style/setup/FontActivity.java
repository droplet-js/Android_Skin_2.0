package com.v7lin.style.setup;

import java.io.File;
import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import com.v7lin.android.env.EnvResourcesManager;
import com.v7lin.android.env.EnvResourcesWrapper;
import com.v7lin.android.env.font.FontFamily;
import com.v7lin.style.app.StyleActivity;
import com.v7lin.style.news.R;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class FontActivity extends StyleActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	private ArrayList<FontData> loadFontDatas(File fontDir, FontFilter fontFilter) {
		ArrayList<FontData> fontDatas = new ArrayList<FontData>();
		final Resources currRes = getResources();
		if (currRes != null && currRes instanceof EnvResourcesWrapper) {
			EnvResourcesWrapper currEnvRes = (EnvResourcesWrapper) currRes;
			Resources oriRes = currEnvRes.getWrapped();
			FontFamily currFontFamily = currEnvRes.getFontFamily();
			
			// 可自由定制
			final int resid = R.string.font_name;

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
					FontFamily fontFamily = EnvResourcesManager.getGlobal().getTopLevelFontFamily(this, fontPath);
					fontDatas.add(new FontData(fontFamily.getFontName(), fontFamily, TextUtils.equals(fontFamily.getFontName(), currFontFamily.getFontName())));
				}
			}
		}
		return fontDatas;
	}

}
