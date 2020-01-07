package com.eno.framework.utils;

import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast 弹窗工具类
 */

public class ToastUtils {
    private static Toast sToast = null;
    private static String strOldMsg;

    private ToastUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static void showToast(String strMsg) {
        if (strMsg == null) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(ContextUtils.getContext(), strMsg, Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            if (!strMsg.equals(strOldMsg)) {
                strOldMsg = strMsg;
            }
            sToast.setText(strMsg);
        }
        sToast.show();
    }
}
