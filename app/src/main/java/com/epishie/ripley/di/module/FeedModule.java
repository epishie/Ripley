/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.di.module;

import com.epishie.ripley.di.scope.FeedScope;
import com.epishie.ripley.entity.repository.FeedRepository;
import com.epishie.ripley.framework.provider.SimpleFeedProvider;
import com.epishie.ripley.framework.reddit.RedditService;
import com.epishie.ripley.interfaceadapter.presenter.MainPresenter;
import com.epishie.ripley.interfaceadapter.presenter.MainPresenterImpl;
import com.epishie.ripley.interfaceadapter.provider.FeedProvider;
import com.epishie.ripley.interfaceadapter.repository.FeedRepositoryImpl;
import com.epishie.ripley.usecase.FetchFeedUseCase;
import com.epishie.ripley.usecase.GetFeedUseCase;
import com.epishie.ripley.usecase.RefreshFeedUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class FeedModule {

    @Provides
    public MainPresenter provideMainPresenter(GetFeedUseCase getFeedUseCase,
                                              FetchFeedUseCase fetchFeedUseCase,
                                              RefreshFeedUseCase refreshFeedUseCase) {
        return new MainPresenterImpl(getFeedUseCase, fetchFeedUseCase, refreshFeedUseCase);
    }

    @FeedScope
    @Provides
    public FeedRepository provideFeedRepository(FeedProvider feedProvider,
                                                @Named("worker")Scheduler workerScheduler) {
        return new FeedRepositoryImpl(feedProvider, workerScheduler);
    }

    @Provides
    public FeedProvider provideFeedProvider(RedditService redditService) {
        return new SimpleFeedProvider(redditService);
    }
}
