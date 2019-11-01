package com.tian.myglide;

import com.tian.myglide.recycler.Resource;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * create by txm  on 2019/10/30
 * desc 活动资源存储
 */
public class ActiveResourceCache {
    private final ResouceReleaseListener mResouceReleaseListener;

     private Map<Key, WeakReferenceResource> mResourceMap = new HashMap<>();
    ReferenceQueue<Resource> mQueue  ;
    Thread clearReferenceQueueThread ;
    private boolean shutDown;

    public ActiveResourceCache(ResouceReleaseListener resouceReleaseListener ) {
        this.mResouceReleaseListener = resouceReleaseListener;
    }

    private ReferenceQueue getQueue() {
        if(mQueue == null) {
            mQueue = new ReferenceQueue();
            clearReferenceQueueThread = new Thread() {
                @Override
                public void run() {
                    while (!shutDown) {
                        try {
                            WeakReferenceResource ref = (WeakReferenceResource) mQueue.remove();
                            mResourceMap.remove(ref.mKey);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
            clearReferenceQueueThread.start();
        }
        return mQueue;
    }

    public Resource remove(Key key) {
        WeakReferenceResource ref = mResourceMap.remove(key);
        if(ref != null) {
            return ref.get();
        }
       return  null;
    }

    public void put(Key key, Resource resource) {
        if(resource != null) {
            resource.setResouceReleaseListener(key,mResouceReleaseListener);
            mResourceMap.put(key, new WeakReferenceResource(key,resource, getQueue()));
        }
    }


    public Resource get(Key key) {
        WeakReferenceResource weakReferenceResource = mResourceMap.get(key);
        if(weakReferenceResource != null) {
            return  weakReferenceResource.get();
        }
        return null;
    }

    public void shutDown() {
        shutDown = true;
        if(clearReferenceQueueThread != null) {
            clearReferenceQueueThread.interrupt();
            try {
                clearReferenceQueueThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class WeakReferenceResource extends  WeakReference<Resource>{
        Key mKey;
        public WeakReferenceResource(Key key,Resource resource, ReferenceQueue queue) {
            super(resource,queue);
            mKey = key;
        }
    }
}
