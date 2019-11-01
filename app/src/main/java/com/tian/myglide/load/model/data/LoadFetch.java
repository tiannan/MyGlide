package com.tian.myglide.load.model.data;

/**
 * create by txm  on 2019/11/1
 * desc 加载数据以及加载结果
 *
 */
public interface LoadFetch<Data>{
    interface  LoadFetchCallBack<Data> {
        public void loadData(Data data);
        public void loadFailed(Exception e);
    }

    public void loadData(LoadFetchCallBack<Data> dataLoadFetchCallBack);
    public void cancel();
}
