/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.usecase;

import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.entity.repository.FeedRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

public class GetFeedUseCase extends UseCase<List<Link>> {

    private final FeedRepository mFeedRepository;

    @Inject
    public GetFeedUseCase(@Named("main") Scheduler mainScheduler,
                          @Named("worker") Scheduler workerScheduler,
                          FeedRepository feedRepository) {
        super(mainScheduler, workerScheduler);
        mFeedRepository = feedRepository;
    }

    @Override
    protected Observable<List<Link>> createObservable() {
        return mFeedRepository.getFeed();
    }

}
