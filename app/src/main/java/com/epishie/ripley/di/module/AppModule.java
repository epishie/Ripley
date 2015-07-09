/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.di.module;

import android.os.Handler;
import android.os.Looper;

import com.epishie.ripley.framework.executor.MainExecutor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class AppModule {

    @Singleton
    @Named("main")
    @Provides
    public Scheduler provideMainScheduler() {
        return Schedulers.from(new MainExecutor());
    }

    @Singleton
    @Named("worker")
    @Provides
    public Scheduler provideWorkerScheduler() {
        return Schedulers.newThread();
    }
}
