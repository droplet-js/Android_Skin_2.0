package com.v7lin.android.app;

import android.app.Application;

/**
 * Google Play
 * https://play.google.com/apps/publish/
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class SuperAppConfig extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();

		// 设置堆利用率，优化 Dalvik 虚拟机的堆内存分配。适用：大型游戏或耗资源的应用
		// VMRuntime.getRuntime().setTargetHeapUtilization(0.75f);
		// 设置最小 heap 内存为 6MB 大小。
		// VMRuntime.getRuntime().setMinimumHeapSize(6* 1024* 1024);
	}
}
