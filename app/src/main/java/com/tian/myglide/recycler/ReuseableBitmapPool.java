package com.tian.myglide.recycler;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * create by txm  on 2019/11/1
 * desc 复用池,使用
 */
public class ReuseableBitmapPool extends LruCache<Integer, Bitmap> implements ReuseableBitmap {
    NavigableMap<Integer,Integer> sizes = new TreeMap<>();
    boolean isRemoved = false;

    public ReuseableBitmapPool(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(Integer key, Bitmap value) {
        return value.getAllocationByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, Integer key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        sizes.remove(key);
        if( !isRemoved) {
            oldValue.recycle();
        }
    }

    @Override
    public Bitmap get(int width, int height, Bitmap.Config config) {
        int size = getBitmapSize(width, height, config);
        Integer key = sizes.ceilingKey(size);
        if(key == null) {
            return null;
        }
        isRemoved = true;
        Bitmap bmp = remove(key);
        isRemoved = false;
        return bmp;
    }

    @Override
    public void put(Bitmap bmp) {
        if(bmp == null) {
            return ;
        }
        if(!bmp.isMutable()) {
            bmp.recycle();
            return ;
        }

        int size = bmp.getAllocationByteCount();
        if(size > maxSize()) {
            bmp.recycle();
            return ;
        }
        put(size,bmp);
        sizes.put(size,0);
    }

    private int getBitmapSize(int width, int height, Bitmap.Config config) {
        return width * height * (config == Bitmap.Config.ARGB_8888 ? 4 : 2);
    }
}
