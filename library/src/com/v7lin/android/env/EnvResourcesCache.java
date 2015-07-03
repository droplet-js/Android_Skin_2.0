package com.v7lin.android.env;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.v7lin.android.env.font.FontFamily;
import com.v7lin.android.env.skin.SkinFamily;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
class EnvResourcesCache {

	private final Map<String, WeakReference<SkinFamily>> mActiveSkinFamily = new HashMap<String, WeakReference<SkinFamily>>();
	private final Map<String, WeakReference<FontFamily>> mActiveFontFamily = new HashMap<String, WeakReference<FontFamily>>();

	private EnvResourcesCache() {
		super();
	}

	public SkinFamily getActiveSkinFamily(String key) {
		WeakReference<SkinFamily> wr = mActiveSkinFamily.get(key);
		return wr != null ? wr.get() : null;
	}

	public void putActiveSkinFamily(String key, SkinFamily family) {
		mActiveSkinFamily.put(key, new WeakReference<SkinFamily>(family));
	}

	public void removeActiveSkinFamily(String key) {
		mActiveSkinFamily.remove(key);
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
