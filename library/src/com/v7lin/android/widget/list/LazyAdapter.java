package com.v7lin.android.widget.list;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class LazyAdapter<D> extends BaseAdapter {

	private LazyCallBack mCallBack;
	private List<D> mDatas;

	public LazyAdapter(LazyCallBack callback) {
		super();
		mCallBack = callback;
	}

	public void setDatas(List<D> datas) {
		this.mDatas = datas;
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	@Override
	public D getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * 可用来去除分割线
	 */
	@Override
	public boolean isEnabled(int position) {
		return super.isEnabled(position);
	}

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return super.getViewTypeCount();
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LazyHolder<D> holder = null;
		if (convertView == null) {
			holder = createCardHolder(parent, getItemViewType(position));
			convertView = holder.getView();
			convertView.setTag(holder);
		} else {
			holder = (LazyHolder<D>) convertView.getTag();
		}
		bindLazyHolder(holder, position);
		return convertView;
	}

	/**
	 * 创建卡片视图的辅助对象
	 */
	LazyHolder<D> createCardHolder(ViewGroup parent, int viewType) {
		LazyHolder<D> holder = onCreateCardHolder(parent, viewType);
		holder.mViewType = viewType;
		return holder;
	}

	/**
	 * 创建卡片视图的辅助对象
	 */
	public abstract LazyHolder<D> onCreateCardHolder(ViewGroup parent, int viewType);

	/**
	 * 绑定卡片信息
	 */
	void bindLazyHolder(LazyHolder<D> holder, int position) {
		D data = getItem(position);
		if (holder.isLazyChanged(data)) {
			holder.mPosition = position;
			if (hasStableIds()) {
				holder.mItemId = getItemId(position);
			}
			holder.mCallBack = mCallBack;
			holder.mData = data;
			holder.bind(data, position);
			
			final boolean isIdle = mCallBack != null && mCallBack.isIdle();
			holder.loadImage(isIdle);// 滑动空闲时，加载图片，优化用户体验

			onBindLazyHolder(holder, position);
		}
	}

	/**
	 * 绑定卡片信息
	 */
	public abstract void onBindLazyHolder(LazyHolder<D> holder, int position);
}
