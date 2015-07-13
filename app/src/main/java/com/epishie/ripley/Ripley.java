/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley;

import android.app.Application;

import com.epishie.ripley.di.HasComponent;
import com.epishie.ripley.di.component.AppComponent;
import com.epishie.ripley.di.component.DaggerAppComponent;
import com.epishie.ripley.di.module.AppModule;

public class Ripley extends Application implements HasComponent<AppComponent> {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup DI component
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule("http://reddit.com"))
                .build();
    }

    @Override
    public AppComponent getComponent() {
        return mComponent;
    }
}
