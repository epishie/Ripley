/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.reddit.model;

import java.util.List;

public class RedditListing<T> {

    private String mModhash;
    private String mBefore;
    private String mAfter;
    private List<RedditObject<T>> mChildren;

    public String getModhash() {
        return mModhash;
    }

    public String getBefore() {
        return mBefore;
    }

    public String getAfter() {
        return mAfter;
    }

    public List<RedditObject<T>> getChildren() {
        return mChildren;
    }
}
