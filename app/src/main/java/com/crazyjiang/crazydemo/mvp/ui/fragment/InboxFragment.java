package com.crazyjiang.crazydemo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.base.BaseFragment;
import com.crazyjiang.crazydemo.di.component.DaggerInboxComponent;
import com.crazyjiang.crazydemo.di.module.InboxModule;
import com.crazyjiang.crazydemo.mvp.contract.InboxContract;
import com.crazyjiang.crazydemo.mvp.presenter.InboxPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class InboxFragment extends BaseFragment<InboxPresenter> implements InboxContract.View {
    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerInboxComponent //if can't find this class, try compile this project
                .builder()
                .appComponent(appComponent)
                .inboxModule(new InboxModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */
    @Override
    public void setData(Object data) {

    }

    @Override
    public void showLoading() {
        // uncomment this code when use SwipeRefreshLayout
        //Observable.just(1)
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        // uncomment this code when use SwipeRefreshLayout
        //Observable.just(1)
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
