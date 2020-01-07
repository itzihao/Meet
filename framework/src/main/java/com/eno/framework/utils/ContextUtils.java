package com.eno.framework.utils;

import android.content.Context;


/**
 * Context 工具类
 */
public class ContextUtils {
    private static Context sContext;

    private ContextUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static Context getContext() {
        if (sContext != null) {
            return sContext;
        }

        throw new NullPointerException("should init first");
    }
}
