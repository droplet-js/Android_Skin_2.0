package com.v7lin.style.app;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public abstract class StyleTaskLoader<D> extends AsyncTaskLoader<D> {

	private D mData;

	public StyleTaskLoader(Context context) {
		super(context);
	}

	protected void ensureInit() {
		
	}

	protected void ensureClear() {
		
	}

	@Override
	public final void deliverResult(D data) {
		D oldData = mData;
		mData = data;

		if (isStarted()) {
			super.deliverResult(data);
		}

		onReleaseResources(oldData);
	}

	@Override
	protected final void onStartLoading() {
		super.onStartLoading();

		ensureInit();

		if (mData != null) {
			deliverResult(mData);
		}

		if (takeContentChanged() || mData == null) {
			forceLoad();
		}
	}

	@Override
	protected final void onStopLoading() {
		super.onStopLoading();
		cancelLoad();
	}

	@Override
	public final void onCanceled(D data) {
		super.onCanceled(data);

		onReleaseResources(data);
	}

	@Override
	protected final void onReset() {
		super.onReset();

		ensureClear();

		// Ensure the loader is stopped
		onStopLoading();

		if (mData != null) {
			onReleaseResources(mData);
			mData = null;
		}
	}

	protected void onReleaseResources(D data) {

	}
}
