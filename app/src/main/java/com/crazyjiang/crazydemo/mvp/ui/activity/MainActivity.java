package com.crazyjiang.crazydemo.mvp.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.constant.Constant;
import com.crazyjiang.crazydemo.app.utils.FragmentUtils;
import com.crazyjiang.crazydemo.di.component.DaggerMainComponent;
import com.crazyjiang.crazydemo.di.module.MainModule;
import com.crazyjiang.crazydemo.mvp.contract.MainContract;
import com.crazyjiang.crazydemo.mvp.presenter.MainPresenter;
import com.crazyjiang.crazydemo.mvp.ui.fragment.InboxFragment;
import com.crazyjiang.crazydemo.mvp.ui.fragment.PostersFragment;
import com.crazyjiang.crazydemo.mvp.ui.fragment.VideosFragment;
import com.huawei.android.pushagent.PushManager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.timchat.model.UserInfo;
import com.tencent.qcloud.timchat.utils.PushUtil;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.tencent.qcloud.ui.NotifyDialog;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, TIMCallBack {
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

        VideosFragment videosFragment;
        PostersFragment postersFragment;
        InboxFragment inboxFragment;
        if (savedInstanceState == null) {
            videosFragment = VideosFragment.newInstance();
            postersFragment = PostersFragment.newInstance();
            inboxFragment = InboxFragment.newInstance();
        } else {
            mIndex = savedInstanceState.getInt(Constant.TAB_INDEX);
            FragmentManager fm = getSupportFragmentManager();
            videosFragment = (VideosFragment) FragmentUtils.findFragment(fm, VideosFragment.class);
            postersFragment = (PostersFragment) FragmentUtils.findFragment(fm, PostersFragment.class);
            inboxFragment = (InboxFragment) FragmentUtils.findFragment(fm, InboxFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(videosFragment);
            mFragments.add(postersFragment);
            mFragments.add(inboxFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);


        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), TIMLogLevel.DEBUG.ordinal());
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        String id = TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
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

    @Override
    public void onError(int i, String s) {
        switch (i) {
            case 6208:
                //离线状态下被其他终端踢下线
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(com.tencent.qcloud.timchat.R.string.kick_logout), getSupportFragmentManager(), (dialog1, which) -> {
                    // navToHome();

                    ArmsUtils.snackbarText("nav to home");
                });
                break;
            case 6200:
                Toast.makeText(this, getString(com.tencent.qcloud.timchat.R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
                // navToLogin();
                ArmsUtils.snackbarText("nav to login");
                break;
            default:
                Toast.makeText(this, getString(com.tencent.qcloud.timchat.R.string.login_error), Toast.LENGTH_SHORT).show();
                // navToLogin();
                ArmsUtils.snackbarText("nav to login");
                break;
        }
    }

    @Override
    public void onSuccess() {
        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
        String deviceMan = android.os.Build.MANUFACTURER;
        //注册小米和华为推送
        if (deviceMan.equals("Xiaomi") && shouldMiInit()) {
            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
        } else if (deviceMan.equals("HUAWEI")) {
            PushManager.requestToken(this);
        }
    }

    /**
     * 判断小米推送是否已经初始化
     */
    private boolean shouldMiInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
