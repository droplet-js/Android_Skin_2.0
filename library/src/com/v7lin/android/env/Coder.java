package com.v7lin.android.env;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class Coder {

	/**
	 * 让一个view在layout里不受大小限制
	 * 
	 * android:clipChildren
	 * 
	 * 1、只需在根节点设置android:clipChildren为false即可，默认为true
	 * 2、可以通过android:layout_gravity控制超出的部分如何显示。
	 * 3、android:clipChildren的意思：是否限制子View在其范围内
	 * 
	 * 在做动画的时候非常有用
	 */
	
	/**
	 * 抗锯齿的几种方法
	 * paint.setAntiAlias(true);
	 * paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	 * canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
	 */
	
	/**
	 * ProgressBar、SeekBar
	 * ProgressDrawable 中使用 ScaleDrawable，在小米 1 上 SeekBar 显示不正常
	 * 故而，ProgressDrawable 还是老实用 ClipDrawable 吧
	 */

}
