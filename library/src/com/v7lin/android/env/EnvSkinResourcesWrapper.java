package com.v7lin.android.env;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;

/**
 * 同名同类型资源 优先级：皮肤插件资源 > 应用自带资源
 * 
 * 皮肤插件，不建议换布局，所以这里不重写 {@link Resources#getLayout(int)} 方法
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public final class EnvSkinResourcesWrapper extends EnvSystemResourcesWrapper {

	private final Context mContext;
	private final String mPackageName;
	private final EnvResourcesManager mResourcesManager;
	private final AtomicBoolean mInitSkinRes = new AtomicBoolean(false);
	private String mSkinPath;
	private Resources mSkinRes;

	public EnvSkinResourcesWrapper(Context context, Resources res, EnvResourcesManager manager) {
		super(context, res, manager);
		mContext = context;
		mPackageName = context.getPackageName();
		mResourcesManager = manager;
	}

	private synchronized void ensureSkinRes(Context context) {
		if (mInitSkinRes.compareAndSet(false, true) || mResourcesManager.isSkinChanged(context, mSkinPath)) {
			mSkinPath = mResourcesManager.getSkinPath(context);
			mSkinRes = mResourcesManager.getResources(context);
		}
	}

	/**
	 * 映射同名同类型资源
	 * 
	 * 皮肤插件包中并不包含所有资源，这导致 R 文件上的资源 id 无法一一对应。 所以这里需要做一次资源映射
	 */
	private EnvRes mappingEnvRes(int resid) {
		EnvRes mapping = null;
		if (mSkinRes != null) {
			String packageName = getResourcePackageName(resid);
			if (TextUtils.equals(packageName, mPackageName)) {
				String typeName = getResourceTypeName(resid);
				String entryName = getResourceEntryName(resid);
				final int mappingid = mSkinRes.getIdentifier(entryName, typeName, packageName);
				mapping = new EnvRes(mappingid);
			}
		}
		return mapping;
	}

	@Override
	public int getColor(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingSystemRes(id);// 系统资源->APP资源
		if (mapping != null && mapping.isValid()) {
			EnvRes mappingEnvRes = mappingEnvRes(mapping.getResid());// APP资源->Skin资源
			if (mappingEnvRes != null && mappingEnvRes.isValid()) {
				mapping = mappingEnvRes;
			}
		} else {
			mapping = mappingEnvRes(id);
		}
		if (mapping != null && mapping.isValid()) {
			try {
				if (mSkinRes != null) {
					try {
						return mSkinRes.getColor(mapping.getResid());
					} catch (NotFoundException e) {
						return super.getColor(mapping.getResid());
					}
				} else {
					return super.getColor(mapping.getResid());
				}
			} catch (NotFoundException e) {
			}
		}
		return super.getColor(id);
	}

	@Override
	public ColorStateList getColorStateList(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingSystemRes(id);// 系统资源->APP资源
		if (mapping != null && mapping.isValid()) {
			EnvRes mappingEnvRes = mappingEnvRes(mapping.getResid());// APP资源->Skin资源
			if (mappingEnvRes != null && mappingEnvRes.isValid()) {
				mapping = mappingEnvRes;
			}
		} else {
			mapping = mappingEnvRes(id);
		}
		if (mapping != null && mapping.isValid()) {
			try {
				if (mSkinRes != null) {
					try {
						return mSkinRes.getColorStateList(mapping.getResid());
					} catch (NotFoundException e) {
						return super.getColorStateList(mapping.getResid());
					}
				} else {
					return super.getColorStateList(mapping.getResid());
				}
			} catch (NotFoundException e) {
			}
		}
		return super.getColorStateList(id);
	}

	@Override
	public Drawable getDrawable(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingSystemRes(id);// 系统资源->APP资源
		if (mapping != null && mapping.isValid()) {
			EnvRes mappingEnvRes = mappingEnvRes(mapping.getResid());// APP资源->Skin资源
			if (mappingEnvRes != null && mappingEnvRes.isValid()) {
				mapping = mappingEnvRes;
			}
		} else {
			mapping = mappingEnvRes(id);
		}
		if (mapping != null && mapping.isValid()) {
			try {
				if (mSkinRes != null) {
					try {
						return mSkinRes.getDrawable(mapping.getResid());
					} catch (NotFoundException e) {
						return super.getDrawable(mapping.getResid());
					}
				} else {
					return super.getDrawable(mapping.getResid());
				}
			} catch (NotFoundException e) {
			}
		}
		return super.getDrawable(id);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	@Override
	public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingSystemRes(id);// 系统资源->APP资源
		if (mapping != null && mapping.isValid()) {
			EnvRes mappingEnvRes = mappingEnvRes(mapping.getResid());// APP资源->Skin资源
			if (mappingEnvRes != null && mappingEnvRes.isValid()) {
				mapping = mappingEnvRes;
			}
		} else {
			mapping = mappingEnvRes(id);
		}
		if (mapping != null && mapping.isValid()) {
			try {
				if (mSkinRes != null) {
					try {
						return mSkinRes.getDrawableForDensity(mapping.getResid(), density);
					} catch (Exception e) {
						return super.getDrawableForDensity(mapping.getResid(), density);
					}
				} else {
					return super.getDrawableForDensity(mapping.getResid(), density);
				}
			} catch (NotFoundException e) {
			}
		}
		return super.getDrawableForDensity(id, density);
	}
	
	@Override
	public String getString(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getString(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getString(id);
	}
	
	@Override
	public String getString(int id, Object... formatArgs) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getString(mapping.getResid(), formatArgs);
			} catch (NotFoundException e) {
			}
		}
		return super.getString(id, formatArgs);
	}

	@Override
	public CharSequence getText(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getText(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getText(id);
	}

	@Override
	public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getQuantityText(mapping.getResid(), quantity);
			} catch (NotFoundException e) {
			}
		}
		return super.getQuantityText(id, quantity);
	}

	@Override
	public CharSequence getText(int id, CharSequence def) {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getText(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getText(id, def);
	}

	@Override
	public CharSequence[] getTextArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getTextArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getTextArray(id);
	}

	@Override
	public String[] getStringArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getStringArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getStringArray(id);
	}

	@Override
	public int[] getIntArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getIntArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getIntArray(id);
	}

	@Override
	public float getDimension(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingSystemRes(id);// 系统资源->APP资源
		if (mapping != null && mapping.isValid()) {
			EnvRes mappingEnvRes = mappingEnvRes(mapping.getResid());// APP资源->Skin资源
			if (mappingEnvRes != null && mappingEnvRes.isValid()) {
				mapping = mappingEnvRes;
			}
		} else {
			mapping = mappingEnvRes(id);
		}
		if (mapping != null && mapping.isValid()) {
			try {
				if (mSkinRes != null) {
					try {
						return mSkinRes.getDimension(mapping.getResid());
					} catch (NotFoundException e) {
						return super.getDimension(mapping.getResid());
					}
				} else {
					return super.getDimension(mapping.getResid());
				}
			} catch (NotFoundException e) {
			}
		}
		return super.getDimension(id);
	}

	@Override
	public int getDimensionPixelOffset(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingSystemRes(id);// 系统资源->APP资源
		if (mapping != null && mapping.isValid()) {
			EnvRes mappingEnvRes = mappingEnvRes(mapping.getResid());// APP资源->Skin资源
			if (mappingEnvRes != null && mappingEnvRes.isValid()) {
				mapping = mappingEnvRes;
			}
		} else {
			mapping = mappingEnvRes(id);
		}
		if (mapping != null && mapping.isValid()) {
			try {
				if (mSkinRes != null) {
					try {
						return mSkinRes.getDimensionPixelOffset(mapping.getResid());
					} catch (NotFoundException e) {
						return super.getDimensionPixelOffset(mapping.getResid());
					}
				} else {
					return super.getDimensionPixelOffset(mapping.getResid());
				}
			} catch (NotFoundException e) {
			}
		}
		return super.getDimensionPixelOffset(id);
	}

	@Override
	public int getDimensionPixelSize(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingSystemRes(id);// 系统资源->APP资源
		if (mapping != null && mapping.isValid()) {
			EnvRes mappingEnvRes = mappingEnvRes(mapping.getResid());// APP资源->Skin资源
			if (mappingEnvRes != null && mappingEnvRes.isValid()) {
				mapping = mappingEnvRes;
			}
		} else {
			mapping = mappingEnvRes(id);
		}
		if (mapping != null && mapping.isValid()) {
			
			try {
				if (mSkinRes != null) {
					try {
						return mSkinRes.getDimensionPixelSize(mapping.getResid());
					} catch (NotFoundException e) {
						return super.getDimensionPixelSize(mapping.getResid());
					}
				} else {
					return super.getDimensionPixelSize(mapping.getResid());
				}
			} catch (NotFoundException e) {
			}
		}
		return super.getDimensionPixelSize(id);
	}

	@Override
	public float getFraction(int id, int base, int pbase) {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getFraction(mapping.getResid(), base, pbase);
			} catch (NotFoundException e) {
			}
		}
		return super.getFraction(id, base, pbase);
	}

	@Override
	public boolean getBoolean(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getBoolean(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getBoolean(id);
	}

	@Override
	public int getInteger(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getInteger(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getInteger(id);
	}

	@Override
	public XmlResourceParser getAnimation(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getAnimation(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getAnimation(id);
	}

	@Override
	public XmlResourceParser getXml(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.getXml(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.getXml(id);
	}

	@Override
	public TypedArray obtainTypedArray(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.obtainTypedArray(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.obtainTypedArray(id);
	}

	@Override
	public InputStream openRawResource(int id, TypedValue value) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.openRawResource(mapping.getResid(), value);
			} catch (NotFoundException e) {
			}
		}
		return super.openRawResource(id, value);
	}

	@Override
	public AssetFileDescriptor openRawResourceFd(int id) throws NotFoundException {
		ensureSkinRes(mContext);
		EnvRes mapping = mappingEnvRes(id);
		if (mapping != null && mapping.isValid() && mSkinRes != null) {
			try {
				return mSkinRes.openRawResourceFd(mapping.getResid());
			} catch (NotFoundException e) {
			}
		}
		return super.openRawResourceFd(id);
	}
}
