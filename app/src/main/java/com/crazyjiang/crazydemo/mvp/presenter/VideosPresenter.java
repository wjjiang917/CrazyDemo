package com.crazyjiang.crazydemo.mvp.presenter;

import android.app.Application;

import com.crazyjiang.crazydemo.mvp.contract.VideosContract;
import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.VideoEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class VideosPresenter extends BasePresenter<VideosContract.Model, VideosContract.View> {
    public static final int PAGE_SIZE = 20;

    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private int currentPage = 1;
    private boolean isFirst = true;

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
        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b
        boolean isEvictCache = !loadMore; //是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存
        if (!loadMore && isFirst) {//默认在第一次下拉刷新时使用缓存
            isFirst = false;
            isEvictCache = false;
        }

        int start = 0;
        if (loadMore) {
            currentPage++;
            start = (currentPage - 1) * PAGE_SIZE;
        } else {
            currentPage = 1;
        }

        mModel.getVideos(start, PAGE_SIZE, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
//                    if (!loadMore)
//                        mRootView.showLoading();//显示上拉刷新的进度条
//                    else
//                        mRootView.startLoadMore();//显示下拉加载更多的进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (!loadMore)
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用Rxlifecycle,使Disposable和Activity一起销毁
                .subscribe(new ErrorHandleSubscriber<QueryResp<List<VideoEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(QueryResp<List<VideoEntity>> listQueryResp) {
                        mRootView.onVideosLoaded(listQueryResp.getRecord());
                    }
                });
    }
}