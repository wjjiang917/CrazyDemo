package com.crazyjiang.crazydemo.app.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

/**
 * Created by Administrator on 2017/7/12.
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment, FragmentLifecycleable {
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    @Inject
    protected P mPresenter;

    private boolean isFirstVisible = true;
    private boolean isViewCreated = false;

    @NonNull
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return this.mLifecycleSubject;
    }

    public BaseFragment() {
        this.setArguments(new Bundle());
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.initView(inflater, container, savedInstanceState);
    }

    public void onDestroy() {
        isFirstVisible = true;
        isViewCreated = false;

        super.onDestroy();
        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }

        this.mPresenter = null;
    }

    public boolean useEventBus() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("xxxxx %s onCreate...", this.getClass().getName());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("xxxxx %s onViewCreated...", this.getClass().getName());

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

        Timber.d("xxxxx %s setUserVisibleHint...", this.getClass().getName());

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
