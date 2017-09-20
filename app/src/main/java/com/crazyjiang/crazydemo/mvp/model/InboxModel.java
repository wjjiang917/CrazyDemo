package com.crazyjiang.crazydemo.mvp.model;

import android.app.Application;

import com.crazyjiang.crazydemo.mvp.contract.InboxContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

@FragmentScope
public class InboxModel extends BaseModel implements InboxContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public InboxModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
}