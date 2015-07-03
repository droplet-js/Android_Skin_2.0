package com.v7lin.android.env;

import android.content.Context;
import android.content.res.Resources;

/**
 * 
 * 
 * @author v7lin Email:v7lin@qq.com
 */
public class EnvSystemResourcesWrapper extends EnvResourcesWrapper {

	private final Context mContext;
	private SystemResMap mSystemResMap = NullResMap.getInstance();

	public EnvSystemResourcesWrapper(Context context, Resources res, EnvResourcesManager manager) {
		super(context, res, manager);
		mContext = context;
	}

	public void setSystemResMap(SystemResMap resMap) {
		mSystemResMap = resMap != null ? resMap : NullResMap.getInstance();
	}

	protected final EnvRes mappingSystemRes(int resid) {
		final String resourcePackageName = super.getResourcePackageName(resid);
		final String resourceTypeName = super.getResourceTypeName(resid);
		final String resourceEntryName = super.getResourceEntryName(resid);
		final int mappingid = mSystemResMap.mapping(mContext, resid, resourcePackageName, resourceTypeName, resourceEntryName);
		EnvRes res = new EnvRes(mappingid);
		return res.isValid() ? res : null;
	}

	@Override
	public String getResourceName(int resid) throws NotFoundException {
		EnvRes mapping = mappingSystemRes(resid);
		if (mapping != null && mapping.isValid()) {
			return super.getResourceName(mapping.getResid());
		}
		return super.getResourceName(resid);
	}

	@Override
	public String getResourcePackageName(int resid) throws NotFoundException {
		EnvRes mapping = mappingSystemRes(resid);
		if (mapping != null && mapping.isValid()) {
			return super.getResourcePackageName(mapping.getResid());
		}
		return super.getResourcePackageName(resid);
	}

	@Override
	public String getResourceTypeName(int resid) throws NotFoundException {
		EnvRes mapping = mappingSystemRes(resid);
		if (mapping != null && mapping.isValid()) {
			return super.getResourceTypeName(mapping.getResid());
		}
		return super.getResourceTypeName(resid);
	}

	@Override
	public String getResourceEntryName(int resid) throws NotFoundException {
		EnvRes mapping = mappingSystemRes(resid);
		if (mapping != null && mapping.isValid()) {
			return super.getResourceEntryName(mapping.getResid());
		}
		return super.getResourceEntryName(resid);
	}
}
