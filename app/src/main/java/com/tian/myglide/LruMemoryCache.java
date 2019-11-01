package com.tian.myglide;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import com.tian.myglide.recycler.Resource;

/**
 * create by txm  on 2019/10/30
 * desc 内存存储，使用最近最少存储
 */
public class LruMemoryCache extends LruCache<Key, Resource> implements MemoryCache {
    private ResourceRemoveListener mResourceRemoveListener;

    public LruMemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(@NonNull Key key, @NonNull Resource value) {
       if(BuildConfig.VERSION_CODE > Build.VERSION_CODES.KITKAT) {
           return value.getBitmap().getAllocationByteCount();
       }
       return value.getBitmap().getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, @NonNull Key key, @NonNull Resource oldValue, @Nullable Resource newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        if(mResourceRemoveListener != null) {
            mResourceRemoveListener.removeResouce(oldValue);
        }
    }

    @Override
    public void put2(Key key, Resource resource){
        put(key, resource);
        if(resource != null) {
            resource.accept();
        }
    }


    @Override
    public Resource remove2(Key key) {
        Resource resource = remove(key);
        if(resource != null) {
            resource.cancelAccept();
        }
        return resource;
    }


    @Override
    public void setRemoveResourceListener(ResourceRemoveListener resourceRemoveListener) {
        mResourceRemoveListener = resourceRemoveListener;
    }

}
