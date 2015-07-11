/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.usecase;

import com.epishie.ripley.entity.repository.FeedRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RefreshFeedUseCaseTest {

    @Mock
    FeedRepository mFeedRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExecute() {
        TestScheduler scheduler = Schedulers.test();
        RefreshFeedUseCase useCase = new RefreshFeedUseCase(scheduler, scheduler, mFeedRepository);

        TestSubscriber<Void> subscriber = new TestSubscriber<>();
        useCase.execute(subscriber);

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        subscriber.assertCompleted();
        verify(mFeedRepository).refresh();
    }
}