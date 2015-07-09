/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.interfaceadapter.repository;

import com.epishie.ripley.entity.model.Feed;
import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.entity.repository.FeedRepository;
import com.epishie.ripley.interfaceadapter.provider.FeedProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.observables.BlockingObservable;
import rx.observers.Subscribers;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FeedRepositoryImplTest {

    @Mock
    FeedProvider mFeedProvider;
    List<Link> mLinks;
    List<Link> mLinksNext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mLinks = new ArrayList<>();
        mLinksNext = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mLinks.add(mock(Link.class));
            mLinksNext.add(mock(Link.class));
        }
        Link duplicate = mock(Link.class);
        mLinks.add(duplicate);
        mLinksNext.add(duplicate);
        Feed feed = new Feed(mLinks, null, "test");
        Feed feedNext = new Feed(mLinksNext, null, null);
        when(mFeedProvider.getAll("")).thenReturn(feed);
        when(mFeedProvider.getAll("test")).thenReturn(feedNext);
    }

    @Test
    public void testGetFeed() throws InterruptedException {
        TestScheduler scheduler = Schedulers.test();
        FeedRepository feedRepository = new FeedRepositoryImpl(mFeedProvider, scheduler);
        Observable<Link> observable = feedRepository.getFeed();
        TestSubscriber<Link> subscriber = new TestSubscriber<>();
        observable.subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe(subscriber);
        feedRepository.fetch();

        scheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        subscriber.assertValueCount(5);
        Link[] links = new Link[mLinks.size()];
        mLinks.toArray(links);
        subscriber.assertValues(links);
        subscriber.assertNotCompleted();
    }

    @Test
    public void testFetch() {
        TestScheduler scheduler = Schedulers.test();
        FeedRepository feedRepository = new FeedRepositoryImpl(mFeedProvider, scheduler);
        Observable<Link> observable = feedRepository.getFeed();
        TestSubscriber<Link> subscriber = new TestSubscriber<>();
        observable.subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe(subscriber);
        feedRepository.fetch();
        feedRepository.fetch();

        scheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        subscriber.assertValueCount(9);
        List<Link> allLinks = new ArrayList<>(mLinks);
        allLinks.addAll(mLinksNext);
        Link[] links = new Link[allLinks.size() - 1];
        allLinks.remove(9);
        allLinks.toArray(links);
        subscriber.assertValues(links);
        subscriber.assertNotCompleted();
    }

    @Test
    public void testStop() {
        TestScheduler scheduler = Schedulers.test();
        FeedRepository feedRepository = new FeedRepositoryImpl(mFeedProvider, scheduler);
        Observable<Link> observable = feedRepository.getFeed();
        TestSubscriber<Link> subscriber = new TestSubscriber<>();
        observable.subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe(subscriber);
        feedRepository.stop();

        scheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        subscriber.assertCompleted();
    }
}