/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.usecase;

import com.epishie.ripley.entity.repository.FeedRepository;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

public class RefreshFeedUseCase extends UseCase<Void> {

    private final FeedRepository mFeedRepository;

    @Inject
    public RefreshFeedUseCase(@Named("main") Scheduler mainScheduler,
                              @Named("worker") Scheduler workerScheduler,
                              FeedRepository feedRepository) {
        super(mainScheduler, workerScheduler);
        mFeedRepository = feedRepository;
    }

    @Override
    protected Observable<Void> createObservable() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mFeedRepository.refresh();
                subscriber.onCompleted();
            }
        });
    }
}
