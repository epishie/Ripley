/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.reddit;

import com.epishie.ripley.entity.model.Link;
import com.epishie.ripley.framework.reddit.model.RedditListing;
import com.epishie.ripley.framework.reddit.model.RedditObject;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

public interface RedditService {

    @GET("/.json")
    RedditObject<RedditListing<Link>> getAll(@Query("after") String name);

    class Builder {

        private Client mClient;

        public Builder setClient(Client client) {
            mClient = client;

            return this;
        }

        public RedditService create(String baseUrl) {
            Gson gson = new GsonBuilder().setFieldNamingStrategy(new FieldNamingStrategy() {
                @Override
                public String translateName(Field f) {
                    String name = f.getName();
                    if (name.startsWith("m")) {
                        name = name.substring(1);
                    }
                    String underscored = name.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
                    return underscored;
                }
            }).create();

            RestAdapter.Builder builder = new RestAdapter.Builder();
            builder.setEndpoint(baseUrl);
            builder.setConverter(new GsonConverter(gson));
            if (mClient != null) {
                builder.setClient(mClient);
            }

            return builder.build().create(RedditService.class);
        }
    }
}
