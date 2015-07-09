/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.provider;

import com.epishie.ripley.entity.model.Feed;
import com.epishie.ripley.framework.reddit.RedditService;
import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.framework.reddit.model.RedditListing;
import com.epishie.ripley.framework.reddit.model.RedditObject;
import com.epishie.ripley.interfaceadapter.provider.FeedProvider;

import java.util.ArrayList;
import java.util.List;

public class SimpleFeedProvider implements FeedProvider {

    private final RedditService mService;

    public SimpleFeedProvider(RedditService service) {
        mService = service;
    }

    @Override
    public Feed getAll(String name) {
        RedditListing<Link> listing = mService.getAll(name).getData();

        List<Link> links = new ArrayList<>();
        for (RedditObject<Link> child : listing.getChildren()) {
            Link link = child.getData();
            links.add(link);
        }

        return new Feed(links, listing.getBefore(), listing.getAfter());
    }
}
