package com.tian.myglide;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Writer;

/**
 * create by txm  on 2019/11/1
 * desc
 */
public interface DiskCache {
    interface Writer{
        boolean write(File file);
    }
    public void put(Key key, Writer writer);
    public File get(Key key);
    public void delete(Key key);
    public void clear();

}
