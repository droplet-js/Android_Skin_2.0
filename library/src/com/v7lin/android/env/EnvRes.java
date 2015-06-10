package com.v7lin.android.env;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvRes {
	
	private final int resid;
	
	public EnvRes(int resid) {
		super();
		this.resid = resid;
	}

	public int getResid() {
		return resid;
	}

	/**
	 * 判断资源映射结果是否有效
	 */
	public boolean isValid() {
		return resid != 0;
	}
}
