package com.v7lin.android.env.webkit;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public abstract class ImageAccessor implements JSAccessor {

	public static final String JS_IMAGE = "javascript:(function(){var objs = document.getElementsByTagName(\"img\"); "
			+ "for(var i=0;i<objs.length;i++){objs[i].onclick=function(){window.ImageAccessor.openImage(this.src);}}})()";

	public abstract void openImage(String src);
}
