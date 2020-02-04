package com.eno.framework.utils;

import java.util.List;

/**
 * Created by Hao on 2020-01-10.
 * Email:itzihao@sina.com
 * TODO
 */
public class CommonUtils {

    /**
     * 检查List是否可用
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        return list != null && list.size() > 0;
    }
}
