/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.entity.model;

public class Vote {

    private final int mUps;
    private final int mDowns;

    public Vote(int ups, int downs) {
        mUps = ups;
        mDowns = downs;
    }

    public int getUps() {
        return mUps;
    }

    public int getDowns() {
        return mDowns;
    }
}
