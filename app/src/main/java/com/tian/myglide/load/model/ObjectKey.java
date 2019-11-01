package com.tian.myglide.load.model;

import com.tian.myglide.Key;
import java.security.MessageDigest;
/**
 * create by txm  on 2019/11/1
 * desc
 */
public class ObjectKey implements Key {
    Object mObject;
    public ObjectKey(Object object) {
        mObject = object;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        md.update(getKeyBytes());
    }

    @Override
    public byte[] getKeyBytes() {
        return mObject.toString().getBytes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ObjectKey objectKey = (ObjectKey) o;
        return mObject.equals(objectKey.mObject);
    }

    @Override
    public int hashCode() {
        return mObject != null ? mObject.hashCode(): 0;
    }
}
