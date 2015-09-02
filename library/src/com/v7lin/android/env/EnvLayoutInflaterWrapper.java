package com.v7lin.android.env;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.v7lin.skin.BuildConfig;

/**
 *
 *
 * @author v7lin E-mail:v7lin@qq.com
 */
public class EnvLayoutInflaterWrapper extends LayoutInflater {

    private static final String[] sClassPrefixArray = {
            // widget
            "android.widget.",
            // webkit
            "android.webkit.",
            // view
            "android.view.",
            // app
            "android.app." };

    private static final List<String> sClassPrefixList = Arrays.asList(sClassPrefixArray);

    private EnvViewMap mViewMap;

    public EnvLayoutInflaterWrapper(LayoutInflater original, Context newContext, EnvViewMap viewMap) {
        super(original, newContext);

        mViewMap = viewMap != null ? viewMap : NullViewMap.getInstance();

        setup();
    }

    private void setup() {
        Factory factory = getFactory();
        if (!(factory instanceof EnvFactory)) {
            setFactory(new EnvFactory(factory));
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new EnvLayoutInflaterWrapper(this, newContext, mViewMap);
    }

    /**
     * 替换掉某些 SDK 中有 BUG 的 View 或 Widget 或者 替换为某些特殊定制的 View 或
     * Widget（兼容高版本的一些特性或时下流行元素等）
     */
    protected String transfer(String name) {
        String transferName = InternalViewMap.getInstance().transfer(name);
        if (TextUtils.isEmpty(transferName)) {
            transferName = mViewMap.transfer(name);
            if (TextUtils.isEmpty(transferName)) {
                transferName = name;
            }
        }
        return transferName;
    }

    class EnvFactory implements Factory {

        private final LayoutInflater.Factory wrapped;

        public EnvFactory(Factory wrapped) {
            super();
            this.wrapped = wrapped;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            View view = null;
            name = transfer(name);
            if (context instanceof LayoutInflater.Factory) {
                view = ((LayoutInflater.Factory) context).onCreateView(name, context, attrs);
            }
            if (view == null && wrapped != null) {
                view = wrapped.onCreateView(name, context, attrs);
            }
            if (view == null) {
                view = createViewOrFailQuietly(name, context, attrs);
            }
            return view;
        }

        private View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
            if (name.contains(".")) {
                return createViewOrFailQuietly(name, null, context, attrs);
            }
            for (final String prefix : sClassPrefixList) {
                final View view = createViewOrFailQuietly(name, prefix, context, attrs);
                if (view != null) {
                    return view;
                }
            }
            return null;
        }

        private View createViewOrFailQuietly(String name, String prefix, Context context, AttributeSet attrs) {
            try {
                return LayoutInflater.from(context).createView(name, prefix, attrs);
            } catch (Exception ignore) {
                if (BuildConfig.DEBUG) {
                    ignore.printStackTrace();
                }
                return null;
            }
        }
    }
}
