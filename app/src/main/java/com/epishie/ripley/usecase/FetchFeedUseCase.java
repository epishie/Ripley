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

public class FetchFeedUseCase extends UseCase<Object> {

    private final FeedRepository mFeedRepository;

    @Inject
    public FetchFeedUseCase(@Named("main") Scheduler mainScheduler,
                            @Named("worker") Scheduler workerScheduler,
                            FeedRepository feedRepository) {
        super(mainScheduler, workerScheduler);
        mFeedRepository = feedRepository;
    }

    @Override
    protected Observable<Object> createObservable() {
        return Observable.create(new Observable.OnSubscribe<Object>() {

            @Override
            public void call(Subscriber<? super Object> subscriber) {
                mFeedRepository.fetch();
                subscriber.onCompleted();
            }
        });
    }
}
