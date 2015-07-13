/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.interfaceadapter.repository;

import com.epishie.ripley.entity.model.Feed;
import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.entity.repository.FeedRepository;
import com.epishie.ripley.interfaceadapter.provider.FeedProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


public class FeedRepositoryImpl implements FeedRepository {

    private final FeedProvider mFeedProvider;
    private final Scheduler mScheduler;
    private final Subject<Message, Message> mPager;
    private Observable<List<Link>> mObservable;
    private String mAfter;

    @Inject
    public FeedRepositoryImpl(FeedProvider feedProvider, Scheduler scheduler) {
        mFeedProvider = feedProvider;
        mScheduler = scheduler;
        mPager = new SerializedSubject<>(ReplaySubject.<Message>create());

        refresh();
    }

    public void refresh() {
        mAfter = "";
        mObservable = Observable.create(new Observable.OnSubscribe<Link>() {
            @Override
            public void call(final Subscriber<? super Link> subscriber) {
                mPager.subscribeOn(mScheduler)
                        .debounce(500, TimeUnit.MILLISECONDS, mScheduler)
                        .subscribe(new Action1<Message>() {
                            @Override
                            public void call(Message message) {
                                switch (message) {
                                    case FETCH:
                                        Feed feed = mFeedProvider.getAll(mAfter);
                                        mAfter = feed.getAfter();
                                        int i = 0;
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
        }).distinct().buffer(1, TimeUnit.SECONDS, 25, mScheduler).filter(new Func1<List<Link>, Boolean>() {

            @Override
            public Boolean call(List<Link> links) {
                return !links.isEmpty();
            }
        }).cache();
        fetch();
    }

    @Override
    public void stop() {
        mPager.onNext(Message.STOP);
    }

    @Override
    public Observable<List<Link>> getFeed() {
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
