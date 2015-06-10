package com.v7lin.android.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class SuperService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
