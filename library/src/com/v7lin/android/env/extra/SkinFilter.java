package com.v7lin.android.env.extra;

import java.io.File;
import java.io.FilenameFilter;

import android.text.TextUtils;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
class SkinFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String filename) {
		return !TextUtils.isEmpty(filename) && filename.toLowerCase().endsWith(".skin");
	}
}
