package com.crazyjiang.crazydemo.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.constant.Constant;
import com.crazyjiang.crazydemo.app.utils.FragmentUtils;
import com.crazyjiang.crazydemo.app.utils.SPUtil;
import com.crazyjiang.crazydemo.di.component.DaggerMainComponent;
import com.crazyjiang.crazydemo.di.module.MainModule;
import com.crazyjiang.crazydemo.mvp.contract.MainContract;
import com.crazyjiang.crazydemo.mvp.presenter.MainPresenter;
import com.crazyjiang.crazydemo.mvp.ui.adapter.ConversationListAdapterEx;
import com.crazyjiang.crazydemo.mvp.ui.fragment.PostersFragment;
import com.crazyjiang.crazydemo.mvp.ui.fragment.VideosFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, IUnReadMessageObserver {
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

    private boolean isDebug = true;

    private ConversationListFragment mConversationListFragment = null;
    private Conversation.ConversationType[] mConversationsTypes = null;

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

                if (TextUtils.isEmpty(SPUtil.getToken(getApplicationContext()))) {
                    launchActivity(new Intent(this, SignInActivity.class));
                    return;
                } else {
                    RongIM.connect(SPUtil.getToken(getApplicationContext()), new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {
                        }

                        @Override
                        public void onSuccess(String s) {
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode e) {
                        }
                    });
                }

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

        Fragment conversationList;

        VideosFragment videosFragment;
        PostersFragment postersFragment;
//        InboxFragment inboxFragment;
        if (savedInstanceState == null) {
            videosFragment = VideosFragment.newInstance();
            postersFragment = PostersFragment.newInstance();
//            inboxFragment = InboxFragment.newInstance();
            conversationList = initConversationList();
        } else {
            mIndex = savedInstanceState.getInt(Constant.TAB_INDEX);
            FragmentManager fm = getSupportFragmentManager();
            videosFragment = (VideosFragment) FragmentUtils.findFragment(fm, VideosFragment.class);
            postersFragment = (PostersFragment) FragmentUtils.findFragment(fm, PostersFragment.class);
//            inboxFragment = (InboxFragment) FragmentUtils.findFragment(fm, InboxFragment.class);
            conversationList = FragmentUtils.findFragment(fm, ConversationListFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(videosFragment);
            mFragments.add(postersFragment);
//            mFragments.add(inboxFragment);
            mFragments.add(conversationList);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
    }

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            if (isDebug) {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM,
                        Conversation.ConversationType.DISCUSSION
                };
            } else {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM
                };
            }
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
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
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);

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
    public void onCountChanged(int count) {

    }
}
