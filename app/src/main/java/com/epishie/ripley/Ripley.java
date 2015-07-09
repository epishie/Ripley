/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley;

import android.app.Application;

import com.epishie.ripley.di.HasComponent;
import com.epishie.ripley.di.component.AppComponent;
import com.epishie.ripley.di.component.DaggerAppComponent;

public class Ripley extends Application implements HasComponent<AppComponent> {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup DI component
        mComponent = DaggerAppComponent.create();
    }

    @Override
    public AppComponent getComponent() {
        return mComponent;
    }
}
