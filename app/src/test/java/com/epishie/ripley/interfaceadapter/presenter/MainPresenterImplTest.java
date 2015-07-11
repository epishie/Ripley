/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.interfaceadapter.presenter;

import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.interfaceadapter.view.MainView;
import com.epishie.ripley.usecase.FetchFeedUseCase;
import com.epishie.ripley.usecase.GetFeedUseCase;
import com.epishie.ripley.usecase.RefreshFeedUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import rx.Subscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MainPresenterImplTest {

    @Mock
    GetFeedUseCase mGetFeedUseCase;
    @Mock
    FetchFeedUseCase mFetchFeedUseCase;
    @Mock
    RefreshFeedUseCase mRefreshFeedUseCase;
    @Mock
    List<Link> mLinks;
    @Mock
    MainView mView;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber<List<Link>> subscriber = (Subscriber<List<Link>>) invocation.getArguments()[0];
                subscriber.onNext(mLinks);
                return null;
            }
        }).when(mGetFeedUseCase).execute(any(Subscriber.class));
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Subscriber<Void> subscriber = (Subscriber<Void>)invocation.getArguments()[0];
                subscriber.onNext(null);
                subscriber.onCompleted();
                return null;
            }
        }).when(mRefreshFeedUseCase).execute(any(Subscriber.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnLoad() {
        MainPresenter mainPresenter = new MainPresenterImpl(mGetFeedUseCase, mFetchFeedUseCase, mRefreshFeedUseCase);
        mainPresenter.setView(mView);
        mainPresenter.onLoad();

        verify(mGetFeedUseCase).execute(any(Subscriber.class));
        verify(mView).showLinks(mLinks);
    }

    @Test
    public void testOnUnload() {
        MainPresenter mainPresenter = new MainPresenterImpl(mGetFeedUseCase, mFetchFeedUseCase, mRefreshFeedUseCase);
        mainPresenter.setView(mView);
        mainPresenter.onLoad();
        mainPresenter.onUnload();

        verify(mGetFeedUseCase).unSubscribe();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnScrolled() {
        MainPresenter mainPresenter = new MainPresenterImpl(mGetFeedUseCase, mFetchFeedUseCase, mRefreshFeedUseCase);
        mainPresenter.setView(mView);
        mainPresenter.onLoad();
        mainPresenter.onScrolled();

        verify(mFetchFeedUseCase).execute(any(Subscriber.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnRefresh() {
        MainPresenter mainPresenter = new MainPresenterImpl(mGetFeedUseCase, mFetchFeedUseCase, mRefreshFeedUseCase);
        mainPresenter.setView(mView);
        mainPresenter.onLoad();
        mainPresenter.onRefresh();

        verify(mRefreshFeedUseCase).execute(any(Subscriber.class));
        verify(mRefreshFeedUseCase).unSubscribe();
        verify(mGetFeedUseCase, times(2)).execute(any(Subscriber.class));
    }
}