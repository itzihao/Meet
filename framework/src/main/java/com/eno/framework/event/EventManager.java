package com.eno.framework.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Hao on 2020-01-07.
 * Email:itzihao@sina.com
 * TODO
 */
public class EventManager {
    /**
     * 注册
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 反注册
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void post(int type) {
        EventBus.getDefault().post(new MessageEvent(type));
    }

    public static void post(MessageEvent event) {
        EventBus.getDefault().post(event);
    }
}
