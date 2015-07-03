package com.v7lin.android.env;

import android.text.TextUtils;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvClassLoaderWrapper extends ClassLoader {
	
	private EnvViewMap mViewMap;

	public EnvClassLoaderWrapper(ClassLoader parentLoader, EnvViewMap viewMap) {
		super(parentLoader);
		mViewMap = viewMap != null ? viewMap : NullViewMap.getInstance();
	}
	
	@Override
	protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
		return super.loadClass(transfer(className), resolve);
	}

	private String transfer(String name) {
		String transferName = InternalViewMap.getInstance().transfer(name);
		if (TextUtils.isEmpty(transferName)) {
			transferName = mViewMap.transfer(name);
			if (TextUtils.isEmpty(transferName)) {
				transferName = name;
			}
		}
		return transferName;
	}
}
