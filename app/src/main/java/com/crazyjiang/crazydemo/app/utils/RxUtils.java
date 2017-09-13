package com.crazyjiang.crazydemo.app.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jess on 11/10/2016 16:39
 * Contact with jess.yan.effort@gmail.com
 */
public class RxUtils {

    private RxUtils() {

    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    view.showLoading();//显示进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    view.hideLoading();//隐藏进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(view));
    }

    /**
     * 此接口已废弃
     *
     * @param view
     * @param <T>
     * @return
     * @see RxLifecycleUtils 使用此类代替
     */
    @Deprecated
    public static <T> LifecycleTransformer<T> bindToLifecycle(IView view) {
        return RxLifecycleUtils.bindToLifecycle(view);
    }

}