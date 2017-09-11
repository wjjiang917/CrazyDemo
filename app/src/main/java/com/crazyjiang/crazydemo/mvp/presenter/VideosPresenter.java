package com.crazyjiang.crazydemo.mvp.presenter;

import android.app.Application;

import com.crazyjiang.crazydemo.app.utils.RxUtils;
import com.crazyjiang.crazydemo.mvp.contract.VideosContract;
import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.Video;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class VideosPresenter extends BasePresenter<VideosContract.Model, VideosContract.View> {
    private static final int PAGE_SIZE = 20;

    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private int currentPage = 1;

    @Inject
    public VideosPresenter(VideosContract.Model model, VideosContract.View rootView,
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

    public void requestData(boolean loadMore) {
        int start = 0;
        if (loadMore) {
            currentPage++;

            start = (currentPage - 1) * PAGE_SIZE;
        } else {
            currentPage = 1;
        }

        mModel.getVideos(start, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    if (!loadMore)
                        mRootView.showLoading();//显示上拉刷新的进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (!loadMore)
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用Rxlifecycle,使Disposable和Activity一起销毁
                .subscribe(new ErrorHandleSubscriber<QueryResp<List<Video>>>(mErrorHandler) {
                    @Override
                    public void onNext(QueryResp<List<Video>> listQueryResp) {
                        mRootView.onVideosLoaded(listQueryResp.getRecord());
                    }
                });
    }
}