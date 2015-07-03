/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.entity.model;

import java.util.List;

public class Feed {

    private final String mBefore;
    private final String mAfter;
    private final List<Link> mLinks;

    public Feed(String before,
                String after,
                List<Link> links) {
        mBefore = before;
        mAfter = after;
        mLinks = links;
    }

    public String getBefore() {
        return mBefore;
    }

    public String getAfter() {
        return mAfter;
    }

    public List<Link> getLinks() {
        return mLinks;
    }
}
