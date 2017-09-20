package com.crazyjiang.crazydemo.mvp.model;

import android.app.Application;

import com.crazyjiang.crazydemo.mvp.contract.PostersContract;
import com.crazyjiang.crazydemo.mvp.model.api.HttpService;
import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.RankLabelEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RoomEntity;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class PostersModel extends BaseModel implements PostersContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PostersModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<QueryResp<RankLabelEntity<RoomEntity>>> getPopularTalents(int start, int offset) {
        return mRepositoryManager.obtainRetrofitService(HttpService.class).queryPopularTalents(start, offset);
    }
}