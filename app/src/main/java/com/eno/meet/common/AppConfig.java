package com.eno.meet.common;

/**
 * Created by Hao on 2019-07-08.
 * Email:itzihao@sina.com
 * Config 配置
 */
public class AppConfig {
    /**
     * Bundle
     */
    public static final class Bd {
        public static final String KEY_POS_INDEX = "key_pos_index";
        public static final String KEY_ORDER_INDEX = "key_order_index";
        public static final String KEY_ORDER_TYPE = "key_order_type";
    }

    /**
     * SP存储
     */
    public static final class Sp {
        public static final String IS_FIRST_RUN_APP = "is_first_run_app";
        public static final String IS_LOGIN = "is_login";
        public static final String PHONE = "phone";
        public static final String TOKEN = "token";
    }

    /**
     * 界面跳转
     */
    public static final class Rc {
        public static final int GRADE_SUBMIT = 1001;
        public static final int REQUEST_CODE_SCAN = 1002;
    }

    /**
     * Event事件
     */
    public static final class Ev {
        public static final int EVENT_TYPE_REFRESH = 100;
    }

    /**
     * 其他事件
     */
    public static final class Other {
        public static final String APPKEY = "1f6e5885b26c24f4";
    }
}
