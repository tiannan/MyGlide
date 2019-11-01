package com.tian.myglide;

import com.tian.myglide.recycler.Resource;
import com.tian.myglide.recycler.ReuseableBitmapPool;
/**
 * create by txm  on 2019/11/1
 * desc
 */
public class CacheTest implements ResouceReleaseListener, MemoryCache.ResourceRemoveListener {

    ReuseableBitmapPool reuseableBitmapPool;
    ActiveResourceCache activeResourceCache;
    LruMemoryCache lruMemoryCache;

    public Resource test(Key key) {
        /**
         * 复用池
         */
        reuseableBitmapPool = new ReuseableBitmapPool(10);
        /**
         * 活动资源
         */
        activeResourceCache = new ActiveResourceCache(this);
        /**
         * 内存缓存
         */
        lruMemoryCache = new LruMemoryCache(10);
        lruMemoryCache.setRemoveResourceListener(this);

        //1.先从活动资源种获取图片
        Resource resource = activeResourceCache.get(key);
        if(resource != null) {
            resource.accept();
            return resource;
        }

        //2.从内存种获取图片
        resource = lruMemoryCache.get(key);
        if(resource != null) {
            resource.accept();
            lruMemoryCache.remove(key);
            activeResourceCache.put(key,resource);
            return resource;
        }
        return null;
    }

    /**
     * 从活动资源中删除，添加到内存缓存
     * @param resource
     */
    @Override
    public void releaseResource(Resource resource) {
        activeResourceCache.remove(resource.getKey());
        lruMemoryCache.put(resource.getKey(),resource);
    }


    /**
     * 从内存缓存种删除，添加到复用池
     * @param resource
     */
    @Override
    public void removeResouce(Resource resource) {
        reuseableBitmapPool.put(resource.getBitmap());
    }
}
