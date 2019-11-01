package com.tian.myglide.recycler;

import android.graphics.Bitmap;

/**
 * create by txm  on 2019/11/1
 * desc
 */
public interface ReuseableBitmap {
    public Bitmap get(int width,int height,Bitmap.Config config);
    public void put(Bitmap bmp);
}
