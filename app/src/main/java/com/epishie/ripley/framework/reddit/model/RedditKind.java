/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.reddit.model;

import com.google.gson.annotations.SerializedName;

public enum RedditKind {

    @SerializedName("Listing")
    LISTING,
    @SerializedName("t3")
    LINK
}
