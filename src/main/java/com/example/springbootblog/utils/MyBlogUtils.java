package com.example.springbootblog.utils;

import java.net.URI;

public class MyBlogUtils {
    public static URI getHost(URI uri) {
        URI efficientURI = null;
        try {
            efficientURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);

        } catch (Throwable var4) {
            efficientURI = null;
        }
        return efficientURI;
    }
}
