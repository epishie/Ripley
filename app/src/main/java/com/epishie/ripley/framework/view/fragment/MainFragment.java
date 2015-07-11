/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epishie.ripley.R;
import com.epishie.ripley.di.HasComponent;
import com.epishie.ripley.di.component.FeedComponent;
import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.framework.view.widget.EndlessRecyclerOnScrollListener;
import com.epishie.ripley.interfaceadapter.presenter.MainPresenter;
import com.epishie.ripley.interfaceadapter.view.MainView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainFragment extends Fragment implements MainView {

    private static final String KEY_LIST_STATE = "list-state";

    @Inject
    protected MainPresenter mPresenter;

    private Adapter mAdapter;
    private RecyclerView mList;
    private Parcelable mListState;
    private SwipeRefreshLayout mSwiper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new Adapter();

        mList = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(lm);
        mList.setAdapter(mAdapter);
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(lm, 2) {
            @Override
            public void onLoadMore(int current_page) {
                mAdapter.setLoaderEnabled(true);
                mPresenter.onScrolled();
            }
        });

        mSwiper = (SwipeRefreshLayout)view.findViewById(R.id.swiper);
        mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mPresenter.onRefresh();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Inject dependencies
        @SuppressWarnings("unchecked")
        HasComponent<FeedComponent> activity = (HasComponent<FeedComponent>)getActivity();
        activity.getComponent()
                .injectFragment(this);

        mPresenter.setView(this);
        mPresenter.onLoad();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(KEY_LIST_STATE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_LIST_STATE, mList.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onDestroy() {
        mPresenter.onUnload();
        super.onDestroy();
    }

    @Override
    public void showLinks(List<Link> links) {
        Log.d("Test", "Size: " + links.size());
        mAdapter.setLoaderEnabled(false);
        if (mSwiper.isRefreshing()) {
            mAdapter.resetLinks(links);
            mSwiper.setRefreshing(false);
        } else {
            mAdapter.addLinks(links);
        }
        if (mListState != null) {
            mList.getLayoutManager().onRestoreInstanceState(mListState);
            mListState = null;
        }
    }

    private static final class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private static final int TYPE_ITEM = 1;
        private static final int TYPE_LOADER = 2;
        private final List<Link> mLinks;
        private boolean mLoaderEnabled = false;

        public Adapter() {
            mLinks = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layout;
            if (viewType == TYPE_LOADER) {
                layout = R.layout.item_loader;
            } else {
                layout = R.layout.item_feed;
            }
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_ITEM) {
                Link link = mLinks.get(position);
                holder.mTitleText.setText(link.getTitle());
            }
        }

        @Override
        public int getItemCount() {
            return mLinks.size() + (mLoaderEnabled ? 1 : 0);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mLinks.size()) {
                return TYPE_LOADER;
            }
            return TYPE_ITEM;
        }

        public void addLinks(List<Link> links) {
            mLinks.addAll(links);
            notifyDataSetChanged();
        }

        public void resetLinks(List<Link> links) {
            mLinks.clear();
            addLinks(links);
        }

        public void setLoaderEnabled(boolean loaderEnabled) {
            mLoaderEnabled = loaderEnabled;
            notifyDataSetChanged();
        }
    }

    private static final class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleText;

        public ViewHolder(View itemView) {
            super(itemView);

            mTitleText = (TextView)itemView.findViewById(R.id.title);
        }
    }
}
