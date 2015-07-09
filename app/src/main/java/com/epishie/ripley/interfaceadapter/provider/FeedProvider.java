/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.interfaceadapter.provider;

import com.epishie.ripley.entity.model.Feed;

public interface FeedProvider {

    Feed getAll(String name);
}
