package com.v7lin.android.env;

import java.io.IOException;
import java.lang.reflect.Field;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.content.res.Resources.NotFoundException;
import android.text.TextUtils;
import android.view.LayoutInflater;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class LayoutInflaterWrapper extends LayoutInflater {

	private static final String TAG_MERGE = generateTagMerge();

	private static String generateTagMerge() {
		try {
			Field field = LayoutInflater.class.getField("TAG_MERGE");
			if (field != null) {
				field.setAccessible(true);
				String tagMerge = String.valueOf(field.get(null));
				return tagMerge;
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return "merge";
	}

	public LayoutInflaterWrapper(Context context) {
		super(context);
	}

	public LayoutInflaterWrapper(LayoutInflater original, Context newContext) {
		super(original, newContext);
	}

	/**
	 * 判断是否以 <merge/> 标签开始
	 */
	public static boolean startWithMergeTag(Context context, int id) {
		try {
			XmlResourceParser parser = context.getResources().getLayout(id);
			int type;
			while ((type = parser.next()) != XmlPullParser.START_TAG && type != XmlPullParser.END_DOCUMENT) {
				// Empty
			}

			if (type != XmlPullParser.START_TAG) {
				return false;
			}

			final String name = parser.getName();
			return TextUtils.equals(TAG_MERGE, name);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
