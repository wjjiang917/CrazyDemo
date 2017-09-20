package com.crazyjiang.crazydemo.mvp.model;

import android.app.Application;

import com.crazyjiang.crazydemo.mvp.contract.VideosContract;
import com.crazyjiang.crazydemo.mvp.model.api.HttpService;
import com.crazyjiang.crazydemo.mvp.model.api.cache.CommonCache;
import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.VideoEntity;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

@FragmentScope
public class VideosModel extends BaseModel implements VideosContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public VideosModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<QueryResp<List<VideoEntity>>> getVideos(int start, int offset, boolean update) {
        // 使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager.obtainRetrofitService(HttpService.class)
                .queryVideos("likes", null, 1, start, offset))
                .flatMap(observable -> mRepositoryManager.obtainCacheService(CommonCache.class)
                        .getVideos(observable, new DynamicKey(start), new EvictDynamicKey(update))
                        .map(Reply::getData));
    }
}