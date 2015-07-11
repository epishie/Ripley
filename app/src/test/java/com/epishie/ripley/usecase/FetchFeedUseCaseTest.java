/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.usecase;

import com.epishie.ripley.entity.repository.FeedRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FetchFeedUseCaseTest {

    @Mock
    FeedRepository mFeedRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() {
        TestScheduler testScheduler = Schedulers.test();
        FetchFeedUseCase fetchFeedUseCase = new FetchFeedUseCase(testScheduler, testScheduler, mFeedRepository);

        TestSubscriber<Object> subscriber = new TestSubscriber<>();
        fetchFeedUseCase.execute(subscriber);

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        verify(mFeedRepository).fetch();
        subscriber.assertCompleted();
    }
}