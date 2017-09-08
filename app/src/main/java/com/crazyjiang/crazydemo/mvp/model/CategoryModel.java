package com.crazyjiang.crazydemo.mvp.model;

import android.app.Application;

import com.crazyjiang.crazydemo.mvp.contract.CategoryContract;
import com.crazyjiang.crazydemo.mvp.model.api.HttpApi;
import com.crazyjiang.crazydemo.mvp.model.entity.GankEntity;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class CategoryModel extends BaseModel implements CategoryContract.Model {
    private Gson mGson;
    private Application mApplication;
    public static final int USERS_PER_PAGESIZE = 10;

    @Inject
    public CategoryModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<GankEntity> gank(String type, String page) {
        return mRepositoryManager.obtainRetrofitService(HttpApi.class).gank(type, USERS_PER_PAGESIZE, page);
    }
}