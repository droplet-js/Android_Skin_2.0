package com.v7lin.android.env.extra;

import android.content.res.Resources;

public interface ExtraCreator<T, D> {
	
	public T createFrom(Resources oriRes, D data, D compare);

}
