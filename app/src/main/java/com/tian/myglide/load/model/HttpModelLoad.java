package com.tian.myglide.load.model;

import android.net.Uri;

import com.tian.myglide.load.model.data.HttpLoadFetcher;

import java.io.InputStream;

/**
 * create by txm  on 2019/11/1
 * desc
 */
public class HttpModelLoad implements ModelLoad<Uri,InputStream> {
    @Override
    public boolean handles(Uri uri) {
        String seheme =  uri.getScheme();
        return seheme.equalsIgnoreCase("http") || seheme.equalsIgnoreCase("https");
    }

    @Override
    public LoadData<InputStream> buildLoad(Uri uri) {
        return new LoadData<>(new ObjectKey(uri), new HttpLoadFetcher(uri));
    }
}
