package com.v7lin.android.env;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class NullViewMap implements EnvViewMap {

	private NullViewMap() {
		super();
	}

	@Override
	public String transfer(String name) {
		return null;
	}

	private static class NullViewMapHolder {
		private static final NullViewMap INSTANCE = new NullViewMap();
	}

	public static NullViewMap getInstance() {
		return NullViewMapHolder.INSTANCE;
	}

}
