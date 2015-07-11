/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.usecase;

import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.entity.repository.FeedRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.*;

public class GetFeedUseCaseTest {

    @Mock
    FeedRepository mFeedRepository;
    @Mock
    List<Link> mLinks;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Observable<List<Link>> observable = Observable.<List<Link>>just(mLinks);
        when(mFeedRepository.getFeed()).thenReturn(observable);
    }

    @Test
    public void testExecute() {
        TestScheduler scheduler = Schedulers.test();
        GetFeedUseCase useCase = new GetFeedUseCase(scheduler, scheduler, mFeedRepository);

        TestSubscriber<List<Link>> subscriber = new TestSubscriber<>();
        useCase.execute(subscriber);

        scheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        verify(mFeedRepository).getFeed();
        subscriber.assertValueCount(1);
        subscriber.assertValue(mLinks);
    }
}