/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.usecase;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

    protected final Scheduler mMainScheduler;
    protected final Scheduler mWorkerScheduler;
    protected Subscription mSubscription = Subscriptions.empty();

    protected UseCase(Scheduler mainScheduler, Scheduler workerScheduler) {
        mMainScheduler = mainScheduler;
        mWorkerScheduler = workerScheduler;
    }

    protected abstract Observable<T> createObservable();

    public void execute(Subscriber<T> subscriber) {
        mSubscription = createObservable().subscribeOn(mWorkerScheduler)
                .observeOn(mMainScheduler)
                .subscribe(subscriber);
    }

    public void unSubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
