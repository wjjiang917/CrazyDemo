package com.crazyjiang.crazydemo.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.mvp.IPresenter;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Administrator on 2017/7/12.
 */

public abstract class BaseFragment<P extends IPresenter> extends RxFragment implements IFragment {
    @Inject
    protected P mPresenter;

    private boolean isFirstVisible = true;
    private boolean isViewCreated = false;

    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        isFirstVisible = true;
        isViewCreated = false;

        super.onDestroy();
        if (mPresenter != null) { //释放资源
            mPresenter.onDestroy();
        }
        this.mPresenter = null;
    }

    public boolean useEventBus() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 界面初始化成功，触发回调方法
        isViewCreated = true;
        setUserVisibleHint(getUserVisibleHint());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // 在界面初始化之前，不调用回调方法
        if (!isViewCreated) {
            return;
        }

        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                onFirstVisible();
            }
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * lazyLoad
     */
    protected void onFirstVisible() {
    }

    /**
     * onResume
     */
    protected void onVisible() {

    }

    /**
     * onPause
     */
    protected void onInvisible() {

    }
}
