/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.provider;

import com.epishie.ripley.entity.model.Feed;
import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.framework.reddit.RedditService;
import com.epishie.ripley.framework.reddit.model.RedditListing;
import com.epishie.ripley.interfaceadapter.provider.FeedProvider;
import com.epishie.ripley.mock.MockFileClient;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SimpleFeedProviderTest {

    @Test
    public void testGetAll() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("after=", "src/test/assets/all.json");
        responseMap.put("after=test", "src/test/assets/all_next.json");
        RedditService service = new RedditService.Builder()
                .setClient(new MockFileClient(responseMap))
                .create("http://reddit.com");
        FeedProvider feedProvider = new SimpleFeedProvider(service);

        Feed feed = feedProvider.getAll("");
        Feed feedNext = feedProvider.getAll("test");

        assertNotNull(feed);
        assertNotNull(feedNext);

        RedditListing<Link> listing = service.getAll("").getData();
        RedditListing<Link> listingNext = service.getAll("test").getData();
        assertEquals(listing.getBefore(), feed.getBefore());
        assertEquals(listing.getAfter(), feed.getAfter());
        assertEquals(listing.getChildren().size(), feed.getLinks().size());
        assertEquals(listingNext.getBefore(), feedNext.getBefore());
        assertEquals(listingNext.getAfter(), feedNext.getAfter());
        assertEquals(listingNext.getChildren().size(), feedNext.getLinks().size());
        for (int i = 0; i < feed.getLinks().size(); i++) {
            Link expected = listing.getChildren().get(i).getData();
            Link actual = feed.getLinks().get(i);
            assertEquals(expected, actual);
        }
        for (int i = 0; i < feedNext.getLinks().size(); i++) {
            Link expected = listingNext.getChildren().get(i).getData();
            Link actual = feedNext.getLinks().get(i);
            assertEquals(expected, actual);
        }
    }
}