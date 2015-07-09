/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.mock;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MockFileClient implements Client {

    private final Map<String, String> mResponseMap;

    public MockFileClient(Map<String, String> responseMap) {
        mResponseMap = responseMap;
    }

    @Override
    public Response execute(Request request) {
        URI uri = URI.create(request.getUrl());

        byte[] body = new byte[]{0};
        String filePath = mResponseMap.get(uri.getQuery());
        if (filePath != null) {
            body = getRawDataFromFile(filePath);
        }

        return new Response(request.getUrl(),
                    200,
                    "",
                    new ArrayList<Header>(),
                    new TypedByteArray("application/json", body));
    }

    private byte[] getRawDataFromFile(String filePath) {
        byte[] raw;
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "r");
            raw = new byte[(int)file.length()];
            file.read(raw);
        } catch (IOException e) {
            e.printStackTrace();
            raw = null;
        }

        return raw;
    }
}
