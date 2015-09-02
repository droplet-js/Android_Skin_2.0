package com.v7lin.android.env.widget;

import android.content.Context;

/**
 * Created by Administrator on 2015/9/1.
 */
public class InternalTransfer {

    public static int transferAttr(Context context, String name) {
        int style = 0;
        try {
            style = Class.forName("com.android.internal.R$attr").getField(name).getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return style;
    }

    public static int transferId(Context context, String name) {
        int id = 0;
        try {
            id = Class.forName("com.android.internal.R$id").getField(name).getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }
}
