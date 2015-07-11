/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.interfaceadapter.presenter;

import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.interfaceadapter.view.MainView;
import com.epishie.ripley.usecase.FetchFeedUseCase;
import com.epishie.ripley.usecase.GetFeedUseCase;
import com.epishie.ripley.usecase.RefreshFeedUseCase;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class MainPresenterImpl implements MainPresenter {

    private final GetFeedUseCase mGetFeedUseCase;
    private final FetchFeedUseCase mFetchFeedUseCase;
    private final RefreshFeedUseCase mRefreshFeedUseCase;
    private MainView mView;

    private Subscriber<List<Link>> mGetFeedSubscriber;

    @Inject
    public MainPresenterImpl(GetFeedUseCase getFeedUseCase,
                             FetchFeedUseCase fetchFeedUseCase,
                             RefreshFeedUseCase refreshFeedUseCase) {
        mGetFeedUseCase = getFeedUseCase;
        mFetchFeedUseCase = fetchFeedUseCase;
        mRefreshFeedUseCase = refreshFeedUseCase;
    }

    @Override
    public void setView(MainView view) {
        mView = view;
    }

    @Override
    public void onLoad() {
        if (mGetFeedSubscriber == null) {
            mGetFeedSubscriber = new Subscriber<List<Link>>() {
                int i = 0;

                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<Link> links) {
                    mView.showLinks(links);
                }
            };
            mGetFeedUseCase.execute(mGetFeedSubscriber);
        }
    }

    @Override
    public void onUnload() {
        mGetFeedUseCase.unSubscribe();
    }

    @Override
    public void onScrolled() {
        mFetchFeedUseCase.execute(new Subscriber<Object>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    @Override
    public void onRefresh() {
        mRefreshFeedUseCase.execute(new Subscriber<Void>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                mRefreshFeedUseCase.unSubscribe();
                mGetFeedUseCase.unSubscribe();
                mGetFeedSubscriber = null;
                onLoad();
            }
        });
    }
}
