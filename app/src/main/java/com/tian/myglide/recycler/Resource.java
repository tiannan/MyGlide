package com.tian.myglide.recycler;

import android.graphics.Bitmap;
import com.tian.myglide.Key;
import com.tian.myglide.ResouceReleaseListener;

/**
 * create by txm  on 2019/10/30
 * desc
 */
public class Resource {
    /**
     * 图片
     */
    private Bitmap mBitmap;

    /**
     * 引用指数
     */
    private int catationIndex;

    /**
     * 键
     */
    private Key mKey;

    /**
     * 释放监听
     */
    ResouceReleaseListener mResouceReleaseListener;

    /**
     * 引用此对象,指数+1
     */
    public void accept() {
        catationIndex ++;
    }

    /**
     * 取消引用，指数-1
     */
    public void cancelAccept() {
        if(null == mBitmap || mBitmap.isRecycled()) {
            throw new IllegalArgumentException("use a recycled bitmap");
        }
        catationIndex --;
        if(catationIndex <= 0) {
            catationIndex = 0;
            recycleBitmap();
        }
    }


    private void recycleBitmap() {
        if(mResouceReleaseListener != null) {
            mResouceReleaseListener.releaseResource(this);
        }
        if(mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
    }


    public  Key getKey() {
        return mKey;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * 判断此对象是否可以被释放
     * @return
     */
    public boolean isRecycled() {
        if(null == mBitmap || mBitmap.isRecycled()) {
            return true;
        }
        return catationIndex == 0;
    }

    /**
     * 主动释放此对象
     */
    public void recycle() {
        if(catationIndex != 0) {
            catationIndex = 0;
        }
        recycleBitmap();
    }

    public ResouceReleaseListener getResouceReleaseListener() {
        return mResouceReleaseListener;
    }

    public void setResouceReleaseListener(Key key,ResouceReleaseListener resouceReleaseListener) {
        this.mKey = key;
        mResouceReleaseListener = resouceReleaseListener;
    }
}
