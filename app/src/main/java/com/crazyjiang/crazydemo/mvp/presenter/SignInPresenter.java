package com.crazyjiang.crazydemo.mvp.presenter;

import android.app.Application;

import com.crazyjiang.crazydemo.app.utils.RxUtils;
import com.crazyjiang.crazydemo.mvp.contract.SignInContract;
import com.crazyjiang.crazydemo.mvp.model.entity.LoginResultEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RongLoginResp;
import com.crazyjiang.crazydemo.mvp.model.entity.SimpResp;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class SignInPresenter extends BasePresenter<SignInContract.Model, SignInContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public SignInPresenter(SignInContract.Model model, SignInContract.View rootView,
                           RxErrorHandler handler, Application application,
                           ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void requestSignIn(String account, String password) {
        mModel.signIn(account, password, "")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用Rxlifecycle,使Disposable和Activity一起销毁
                .subscribe(new ErrorHandleSubscriber<SimpResp<LoginResultEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(SimpResp<LoginResultEntity> resp) {
                        mRootView.onResponse(resp);
                    }
                });
    }

    public void requestRongSignIn(Integer userId, String name, String image) {
        mModel.signInRong(userId, name, image)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用Rxlifecycle,使Disposable和Activity一起销毁
                .subscribe(new ErrorHandleSubscriber<RongLoginResp>(mErrorHandler) {
                    @Override
                    public void onNext(RongLoginResp rongLoginResp) {
                        mRootView.onRongResponse(rongLoginResp);
                    }
                });
    }
}
