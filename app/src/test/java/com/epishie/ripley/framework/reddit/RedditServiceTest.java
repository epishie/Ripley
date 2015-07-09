/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.reddit;

import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.framework.reddit.model.RedditListing;
import com.epishie.ripley.framework.reddit.model.RedditObject;
import com.epishie.ripley.mock.MockFileClient;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RedditServiceTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetAll() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("after=", "src/test/assets/all.json");
        RedditService service = new RedditService.Builder()
                .setClient(new MockFileClient(responseMap))
                .create("http://reddit.com");

        RedditObject<RedditListing<Link>> response = service.getAll("");

        assertNotNull(response);
    }
}