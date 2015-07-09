/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.di.component;

import com.epishie.ripley.di.module.AppModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    @Named("main")
    Scheduler getMainScheduler();
    @Named("worker")
    Scheduler getWorkerScheduler();
}
