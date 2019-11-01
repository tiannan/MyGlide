package com.tian.myglide;

import java.security.MessageDigest;

/**
 * create by txm  on 2019/10/30
 * desc
 */
public interface Key {
    void updateDiskCacheKey(MessageDigest md);

    byte[] getKeyBytes();
}
