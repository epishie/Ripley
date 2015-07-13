/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.di.module;

import com.epishie.ripley.framework.executor.MainExecutor;
import com.epishie.ripley.framework.reddit.RedditService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@Module
public class AppModule {

    private final String mRedditUrl;

    public AppModule(String redditUrl) {
        mRedditUrl = redditUrl;
    }

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
        return Schedulers.io();
    }

    @Singleton
    @Provides
    public RedditService provideRedditService() {
        return new RedditService.Builder().create(mRedditUrl);
    }
}
