package com.v7lin.style.global;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

import com.v7lin.android.os.env.PathUtils;
import com.v7lin.style.app.StyleConst;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class GlobalManager implements StyleConst {

	/**
	 * 获取配置信息
	 */
	public static void ensureConfig(Context context, int configResid) {
		String rtlPath = null;
		XmlResourceParser parser = context.getResources().getXml(configResid);
		try {
			boolean eof = false;
			while (!eof) {
				switch (parser.getEventType()) {
				case XmlPullParser.START_TAG: {
					if (TextUtils.equals(parser.getName(), KEY_CONFIG_RTLPATH)) {
						rtlPath = parser.nextText();
					}
					break;
				}
				case XmlPullParser.END_DOCUMENT: {
					eof = true;
					break;
				}
				default: {
					break;
				}
				}
				parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (TextUtils.isEmpty(rtlPath)) {
			rtlPath = DEFAULT_CONFIG_RTLPATH;
		}
		// 设置 APP 路径
		PathUtils.setAppRtlPath(rtlPath);
	}
}
