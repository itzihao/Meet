package com.eno.meet.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by Hao on 2020-01-08.
 * Email:itzihao@sina.com
 * TODO
 */
public class CloudService extends Service {
    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }
}
