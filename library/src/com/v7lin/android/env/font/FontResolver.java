package com.v7lin.android.env.font;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public interface FontResolver {

	public FontFamily getDefaultFont();

	public FontFamily getSerifFont();

	public FontFamily getSansSerifFont();

	public FontFamily getMonoSpaceFont();

	public FontFamily getFont(String name);

}
