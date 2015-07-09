/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class MainExecutor implements Executor {

    private final Handler mHandler;

    public MainExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}
