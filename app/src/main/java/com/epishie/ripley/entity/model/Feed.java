/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.entity.model;

import java.util.List;

public class Feed {

    private final String mBefore;
    private final String mAfter;
    private final List<Link> mLinks;

    public Feed(List<Link> links, String before, String after) {
        mLinks = links;
        mBefore = before;
        mAfter = after;
    }

    public List<Link> getLinks() {
        return mLinks;
    }

    public String getBefore() {
        return mBefore;
    }

    public String getAfter() {
        return mAfter;
    }
}
