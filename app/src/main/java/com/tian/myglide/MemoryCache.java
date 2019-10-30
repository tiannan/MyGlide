package com.tian.myglide;

import com.tian.myglide.recycler.Resource;

/**
 * create by txm  on 2019/10/30
 * desc
 */
public interface MemoryCache {
    public interface ResourceRemoveListener {
        public void removeResouce(Resource resource);
    }
    public void put2(Key key, Resource resource);
    public Resource remove2(Key key);
    public Resource get(Key key);

    public void setRemoveResourceListener(ResourceRemoveListener removeResourceListener);
}
