/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.entity.repository;

import com.epishie.ripley.entity.model.Link;

import rx.Observable;

public interface FeedRepository {

    Observable<Link> getFeed();
    void fetch();
    void refresh();
    void stop();
}
