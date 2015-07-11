/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.interfaceadapter.presenter;

import com.epishie.ripley.interfaceadapter.view.MainView;

public interface MainPresenter {

    void setView(MainView view);

    void onLoad();
    void onUnload();

    void onScrolled();
    void onRefresh();
}
