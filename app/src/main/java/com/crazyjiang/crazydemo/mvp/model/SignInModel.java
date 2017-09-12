package com.crazyjiang.crazydemo.mvp.model;

import android.app.Application;

import com.crazyjiang.crazydemo.mvp.contract.SignInContract;
import com.crazyjiang.crazydemo.mvp.model.api.HttpService;
import com.crazyjiang.crazydemo.mvp.model.entity.LoginResultEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RongLoginResp;
import com.crazyjiang.crazydemo.mvp.model.entity.SimpResp;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class SignInModel extends BaseModel implements SignInContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SignInModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<SimpResp<LoginResultEntity>> signIn(String account, String password, String imei) {
        return mRepositoryManager.obtainRetrofitService(HttpService.class).signIn(account, password, imei);
    }

    @Override
    public Observable<RongLoginResp> signInRong(Integer userId, String name, String image) {
        return mRepositoryManager.obtainRetrofitService(HttpService.class).signInRong(userId, name, image);
    }
}