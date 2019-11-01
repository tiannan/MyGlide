package com.tian.myglide.load.model;

import com.tian.myglide.Key;
import com.tian.myglide.load.model.data.LoadFetch;

/**
 * create by txm  on 2019/11/1
 * desc model 数据来源  Data是指加载成功后数据格式(inputstream  byte[] )
 */
public interface ModelLoad<Model,Data> {

    class LoadData<Data>{
        Key key;

        public LoadFetch<Data>  mLoadFetch;

        public LoadData(Key key,LoadFetch<Data> loadFetch) {
            this.key = key;
            this.mLoadFetch = loadFetch;
        }
    }

    /**
     * 是否符合此加载方式
     * @param model
     * @return
     */
    boolean handles(Model model);

    /**
     * 创建加载数据方式
     * @param
     */
    LoadData<Data>  buildLoad(Model model);
}
