/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.di.component;

import com.epishie.ripley.di.module.FeedModule;
import com.epishie.ripley.di.scope.FeedScope;
import com.epishie.ripley.framework.view.fragment.MainFragment;

import dagger.Component;

@FeedScope
@Component(
        dependencies = AppComponent.class,
        modules = FeedModule.class
)
public interface FeedComponent {

    void injectFragment(MainFragment fragment);
}
