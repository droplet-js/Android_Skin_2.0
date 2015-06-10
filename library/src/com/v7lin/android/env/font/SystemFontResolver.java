package com.v7lin.android.env.font;

import android.util.Log;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class SystemFontResolver implements FontResolver {

	public SystemFontResolver() {
		super();
	}

	@Override
	public FontFamily getDefaultFont() {
		return FontFamily.DEFAULT_FONT;
	}

	@Override
	public FontFamily getSerifFont() {
		return FontFamily.SERIF_FONT;
	}

	@Override
	public FontFamily getSansSerifFont() {
		return FontFamily.SANSSERIF_FONT;
	}

	@Override
	public FontFamily getMonoSpaceFont() {
		return FontFamily.MONOSPACE_FONT;
	}

	@Override
	public FontFamily getFont(String name) {
		if (name != null && name.length() > 0) {
			String[] parts = name.split(",(\\s)*");
			for (int i = 0; i < parts.length; i++) {
				String fontName = parts[i];
				if (fontName.startsWith("\"") && fontName.endsWith("\"")) {
					fontName = fontName.substring(1, fontName.length() - 1);
				}
				if (fontName.startsWith("\'") && fontName.endsWith("\'")) {
					fontName = fontName.substring(1, fontName.length() - 1);
				}
				FontFamily fam = resolveFont(fontName);
				if (fam != null) {
					return fam;
				}
			}
		}
		return getDefaultFont();
	}

	private FontFamily resolveFont(String name) {
		Log.d("SystemFontResolver", "Trying to resolve font " + name);
		if (name.equalsIgnoreCase("serif")) {
			return getSerifFont();
		} else if (name.equalsIgnoreCase("sans-serif")) {
			return getSansSerifFont();
		} else if (name.equalsIgnoreCase("monospace")) {
			return getMonoSpaceFont();
		}
		return null;
	}
}
