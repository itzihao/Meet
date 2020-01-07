package com.eno.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * TODO
 */
public class DialogView extends Dialog {

    public DialogView(Context mContext, int layout, int style, int gravity) {
        super(mContext, style);
        setContentView(layout);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
    }
}
