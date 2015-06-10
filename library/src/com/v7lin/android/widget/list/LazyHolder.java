package com.v7lin.android.widget.list;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class LazyHolder<D> {

	int mViewType;
	int mPosition;
	long mItemId;
	D mData;
	LazyCallBack mCallBack;

	/**
	 * 用来判断视图中的数据与新数据是否相同
	 * 
	 * 若相同，则直接使用视图，不再需要视图绑定等。这样可以减少视图创建和视图刷新等。
	 */
	boolean isLazyChanged(D data) {
		return (mData == null && data != null) || (mData != null && data != null && !mData.equals(data));
	}

	void bind(D data, int position) {
		onBind(data, position);
	}

	/**
	 * 绑定视图
	 */
	public abstract void onBind(D data, int position);

	/**
	 * 取得视图
	 */
	public abstract View getView();

	/**
	 * 加载视图内所有图片
	 */
	void loadImage(boolean isIdle) {
		onLoadImage(isIdle);
	}

	/**
	 * 加载视图内所有图片
	 */
	public abstract void onLoadImage(boolean isIdle);

	/**
	 * 图片视图绑定图片URL，这样可以减少图片刷新，避免图片闪烁
	 */
	protected final void bindLazyTag(ImageView imageView, String imgUrl) {
		LazyTag lazyTag = null;
		Object tag = imageView.getTag();
		if (tag != null && tag instanceof LazyTag) {
			lazyTag = (LazyTag) tag;
			if (!TextUtils.equals(imgUrl, lazyTag.imgUrl)) {
				lazyTag.imgUrl = imgUrl;
				lazyTag.hasImg = false;
				imageView.setImageDrawable(null);
				imageView.setTag(lazyTag);
			}
		} else {
			lazyTag = new LazyTag();
			lazyTag.imgUrl = imgUrl;
			lazyTag.hasImg = false;
			imageView.setImageDrawable(null);
			imageView.setTag(lazyTag);
		}
	}

	/**
	 * 加载图片
	 */
	protected final void scheduleImage(ImageView imageView, String imgUrl, int type, boolean isIdle) {
		if (mCallBack != null) {
			mCallBack.scheduleImage(imageView, imgUrl, type, isIdle);
		}
	}
}
