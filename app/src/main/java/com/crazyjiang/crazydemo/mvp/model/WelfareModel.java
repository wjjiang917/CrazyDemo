package com.crazyjiang.crazydemo.mvp.model;

import android.app.Application;
import android.os.Message;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.crazyjiang.crazydemo.app.GreenDaoHelper;
import com.crazyjiang.crazydemo.app.greendao.DaoGankEntityDao;
import com.crazyjiang.crazydemo.mvp.contract.WelfareContract;
import com.crazyjiang.crazydemo.mvp.model.api.HttpApi;
import com.crazyjiang.crazydemo.mvp.model.entity.DaoGankEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.GankEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class WelfareModel extends BaseModel implements WelfareContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public WelfareModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<GankEntity> getRandomGirl() {
        Observable<GankEntity> randomGirl = mRepositoryManager.obtainRetrofitService(HttpApi.class).getRandomGirl();
        return randomGirl;
    }

    @Override
    public Message addGankEntity(DaoGankEntity daoGankEntity) {
        Message message = new Message();
        List<DaoGankEntity> list = GreenDaoHelper.getDaoSession().getDaoGankEntityDao()
                .queryBuilder()
                .where(DaoGankEntityDao.Properties._id.eq(daoGankEntity._id))
                .list();
        if (list.size() > 0) {
            message.what = 101;
        } else {
            long insert = GreenDaoHelper.getDaoSession().getDaoGankEntityDao().insert(daoGankEntity);
            if (insert > 0) {
                message.what = 102;
            } else {
                message.what = 103;
            }
        }
        return message;
    }
}