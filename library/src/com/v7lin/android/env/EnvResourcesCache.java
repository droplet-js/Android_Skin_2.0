package com.v7lin.android.env;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;

import com.v7lin.android.env.font.FontFamily;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
class EnvResourcesCache {

	private final Map<String, WeakReference<Resources>> mActiveResources = new HashMap<String, WeakReference<Resources>>();
	private final Map<String, WeakReference<FontFamily>> mActiveFontFamily = new HashMap<String, WeakReference<FontFamily>>();

	private EnvResourcesCache() {
		super();
	}

	public Resources getActiveResources(String key) {
		WeakReference<Resources> wr = mActiveResources.get(key);
		return wr != null ? wr.get() : null;
	}

	public void putActiveResources(String key, Resources res) {
		mActiveResources.put(key, new WeakReference<Resources>(res));
	}

	public void removeActiveResources(String key) {
		mActiveResources.remove(key);
	}

	public FontFamily getActiveFontFamily(String key) {
		WeakReference<FontFamily> wr = mActiveFontFamily.get(key);
		return wr != null ? wr.get() : null;
	}

	public void putActiveFontFamily(String key, FontFamily family) {
		mActiveFontFamily.put(key, new WeakReference<FontFamily>(family));
	}

	public void removeActiveFontFamily(String key) {
		mActiveFontFamily.remove(key);
	}

	private static class EnvResourcesCacheHolder {
		public static final EnvResourcesCache INSTANCE = new EnvResourcesCache();
	}

	public static EnvResourcesCache getInstance() {
		return EnvResourcesCacheHolder.INSTANCE;
	}
}
