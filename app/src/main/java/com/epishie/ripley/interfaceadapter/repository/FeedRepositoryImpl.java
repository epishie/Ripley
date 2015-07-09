/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.interfaceadapter.repository;

import android.animation.FloatEvaluator;

import com.epishie.ripley.entity.model.Feed;
import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.entity.repository.FeedRepository;
import com.epishie.ripley.interfaceadapter.provider.FeedProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


public class FeedRepositoryImpl implements FeedRepository {

    private final FeedProvider mFeedProvider;
    private final Scheduler mScheduler;
    private final Subject<Message, Message> mPager;
    private Observable<Link> mObservable;
    private String mAfter;

    @Inject
    public FeedRepositoryImpl(FeedProvider feedProvider, Scheduler scheduler) {
        mFeedProvider = feedProvider;
        mScheduler = scheduler;
        mPager = new SerializedSubject<>(ReplaySubject.<Message>create());
        mAfter = "";

        refresh();
    }

    public void refresh() {
        mObservable = Observable.create(new Observable.OnSubscribe<Link>() {
            @Override
            public void call(final Subscriber<? super Link> subscriber) {
                mPager.observeOn(mScheduler)
                        .subscribeOn(mScheduler)
                        .subscribe(new Action1<Message>() {
                            @Override
                            public void call(Message message) {
                                switch (message) {
                                    case FETCH:
                                        Feed feed = mFeedProvider.getAll(mAfter);
                                        mAfter = feed.getAfter();
                                        for (Link link : feed.getLinks()) {
                                            subscriber.onNext(link);
                                        }
                                        break;
                                    case STOP:
                                        subscriber.onCompleted();
                                        break;
                                }
                            }
                        });
            }
        }).cache().distinct();
    }

    @Override
    public void stop() {
        mPager.onNext(Message.STOP);
    }

    @Override
    public Observable<Link> getFeed() {
        return mObservable;
    }

    @Override
    public void fetch() {
        mPager.onNext(Message.FETCH);
    }

    private enum Message {
        FETCH,
        STOP
    }
}
