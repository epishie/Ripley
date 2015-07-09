/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.reddit.model;

public class RedditObject<T> {

    private RedditKind mKind;
    private T mData;

    public RedditKind getKind() {
        return mKind;
    }

    public T getData() {
        return mData;
    }
}
