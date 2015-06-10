package com.v7lin.style.event;

import android.os.Bundle;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public interface EventReceiver {
	public void onEvent(String action, Bundle extras);
}
