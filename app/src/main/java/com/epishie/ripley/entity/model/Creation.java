/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.entity.model;

public class Creation {

    private long mCreated;
    private long mCreatedUtc;

    public Creation(long created, long createdUtc) {
        mCreated = created;
        mCreatedUtc = createdUtc;
    }

    public long getCreated() {
        return mCreated;
    }

    public long getCreatedUtc() {
        return mCreatedUtc;
    }
}
