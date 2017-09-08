package com.crazyjiang.crazydemo.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.constant.Constant;
import com.crazyjiang.crazydemo.app.utils.FragmentUtils;
import com.crazyjiang.crazydemo.di.component.DaggerMainComponent;
import com.crazyjiang.crazydemo.di.module.MainModule;
import com.crazyjiang.crazydemo.mvp.contract.MainContract;
import com.crazyjiang.crazydemo.mvp.presenter.MainPresenter;
import com.crazyjiang.crazydemo.mvp.ui.fragment.CollectFragment;
import com.crazyjiang.crazydemo.mvp.ui.fragment.HomeFragment;
import com.crazyjiang.crazydemo.mvp.ui.fragment.WelfareFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;

    private RxPermissions mRxPermissions;
    private List<Integer> mTitles;
    private List<Fragment> mFragments;
    private List<Integer> mNavIds;
    private int mIndex = 0;

    private OnTabSelectListener mOnTabSelectListener = tabId -> {
        switch (tabId) {
            case R.id.tab_videos:
                mIndex = 0;
                break;
            case R.id.tab_talents:
                mIndex = 1;
                break;
            case R.id.tab_inbox:
                mIndex = 2;
                break;
        }
        mToolbarTitle.setText(mTitles.get(mIndex));
        FragmentUtils.hideAllShowFragment(mFragments.get(mIndex));
    };

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        this.mRxPermissions = new RxPermissions(this);
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        toolbarBack.setVisibility(View.GONE);
        mPresenter.requestPermissions();
        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.title_video);
            mTitles.add(R.string.title_talents);
            mTitles.add(R.string.title_inbox);
        }
        if (mNavIds == null) {
            mNavIds = new ArrayList<>();
            mNavIds.add(R.id.tab_videos);
            mNavIds.add(R.id.tab_talents);
            mNavIds.add(R.id.tab_inbox);
        }

        HomeFragment homeFragment;
        WelfareFragment welfareFragment;
        CollectFragment collectFragment;
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            welfareFragment = WelfareFragment.newInstance();
            collectFragment = CollectFragment.newInstance();
        } else {
            mIndex = savedInstanceState.getInt(Constant.TAB_INDEX);
            FragmentManager fm = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(fm, HomeFragment.class);
            welfareFragment = (WelfareFragment) FragmentUtils.findFragment(fm, WelfareFragment.class);
            collectFragment = (CollectFragment) FragmentUtils.findFragment(fm, CollectFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(welfareFragment);
            mFragments.add(collectFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        finish();
    }


    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存当前Activity显示的Fragment索引
        outState.putInt(Constant.TAB_INDEX, mIndex);
    }

    @Override
    protected void onDestroy() {
        // fix Memory Leak / ANR from InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            InputMethodManager.class.getDeclaredMethod("windowDismissed", IBinder.class)
                    .invoke(imm, getWindow().getDecorView().getWindowToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
        this.mRxPermissions = null;
        this.mTitles = null;
        this.mFragments = null;
        this.mNavIds = null;
    }
}
