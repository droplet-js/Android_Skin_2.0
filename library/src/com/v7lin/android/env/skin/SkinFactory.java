package com.v7lin.android.env.skin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class SkinFactory {

	public static boolean isValid(SkinFamily family) {
		boolean isValid = false;
		try {
			if (family != null && family.getResources() != null && family.getResources().getAssets() != null) {
				AssetManager assets = family.getResources().getAssets();
				Class<?> clazz = assets.getClass();
				Method method = clazz.getDeclaredMethod("isUpToDate");
				Object object = method.invoke(assets);
				isValid = Boolean.valueOf(String.valueOf(object)).booleanValue();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	public static SkinFamily makeSkin(Context context, String skinPath) {
		SkinFamily family = null;
		try {
			if (!TextUtils.isEmpty(skinPath)) {
				Class<?> clazz = AssetManager.class;
				AssetManager skinAsset = (AssetManager) clazz.newInstance();
				Method method = clazz.getDeclaredMethod("addAssetPath", String.class);
				method.invoke(skinAsset, skinPath);
				// 由于这里的资源都是非系统级别的，不会被缓存到Resources的static成员常量里
				// 即Resources的startPreloading和finishPreloading已在ZygoteInit中被调用完毕，一些启动所需的系统资源被缓存到Resources的static成员常量里
				// 所以可以不用EnvThirdResources
				Resources resources = new Resources(skinAsset, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
				family = new SkinFamily(skinPath, resources);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return family;
	}
}
