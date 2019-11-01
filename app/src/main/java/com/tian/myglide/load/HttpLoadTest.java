package com.tian.myglide.load;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.tian.myglide.load.model.HttpModelLoad;
import com.tian.myglide.load.model.ModelLoad;
import com.tian.myglide.load.model.data.LoadFetch;

import java.io.InputStream;

/**
 * create by txm  on 2019/11/1
 * desc
 */
public class HttpLoadTest {
    public void test() {
        Uri uri = Uri.parse("http://baidu.com");
        HttpModelLoad  httpModelLoad = new HttpModelLoad();
        if(httpModelLoad.handles(uri)) {
            ModelLoad.LoadData<InputStream>  loadData = httpModelLoad.buildLoad(uri);
            loadData.mLoadFetch.loadData(new LoadFetch.LoadFetchCallBack<InputStream>() {
                @Override
                public void loadData(InputStream inputStream) {
                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                }

                @Override
                public void loadFailed(Exception e) {

                }
            });
        }
    }


}
