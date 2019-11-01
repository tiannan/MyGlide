package com.tian.myglide.load.model.data;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * create by txm  on 2019/11/1
 * desc
 */
public class HttpLoadFetcher implements LoadFetch<InputStream> {
    private Uri uri;
    private boolean isCanceled;

    public HttpLoadFetcher(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void loadData(LoadFetchCallBack<InputStream> callBack) {
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            is = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            if (isCanceled) {
                return;
            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                callBack.loadData(is);
            } else {
                callBack.loadFailed(new RuntimeException(connection.getResponseMessage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void cancel() {
        this.isCanceled = true;
    }
}
