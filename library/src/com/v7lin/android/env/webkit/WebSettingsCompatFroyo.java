package com.v7lin.android.env.webkit;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;

import com.v7lin.android.env.webkit.WebSettingsCompat.PluginStateCompat;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
@TargetApi(Build.VERSION_CODES.FROYO)
@SuppressWarnings("deprecation")
public class WebSettingsCompatFroyo {

	public static void setPluginState(WebSettings settings, PluginStateCompat state) {
		switch (state) {
		case ON: {
			settings.setPluginState(PluginState.ON);
			break;
		}
		case ON_DEMAND: {
			settings.setPluginState(PluginState.ON_DEMAND);
			break;
		}
		case OFF: {
			settings.setPluginState(PluginState.OFF);
			break;
		}
		default: {
			break;
		}
		}
	}
}
