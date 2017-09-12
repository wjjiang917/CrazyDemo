package com.crazyjiang.crazydemo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.utils.ImageUtil;
import com.crazyjiang.crazydemo.app.utils.SPUtil;
import com.crazyjiang.crazydemo.di.component.DaggerSignInComponent;
import com.crazyjiang.crazydemo.di.module.SignInModule;
import com.crazyjiang.crazydemo.mvp.contract.SignInContract;
import com.crazyjiang.crazydemo.mvp.model.entity.LoginResultEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RongLoginResp;
import com.crazyjiang.crazydemo.mvp.model.entity.SimpResp;
import com.crazyjiang.crazydemo.mvp.model.entity.UserEntity;
import com.crazyjiang.crazydemo.mvp.presenter.SignInPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SignInActivity extends BaseActivity<SignInPresenter> implements SignInContract.View {
    @BindView(R.id.tv_account)
    AutoCompleteTextView tvAccount;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSignInComponent //if can't find this class, try compile this project
                .builder()
                .appComponent(appComponent)
                .signInModule(new SignInModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_signin; // if you want to setContentView(id) yourself, return 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_login)
    public void clickSignIn(View view) {
        if (!TextUtils.isEmpty(tvAccount.getText().toString()) && !TextUtils.isEmpty(tvPassword.getText().toString()))
            mPresenter.requestSignIn(tvAccount.getText().toString(), tvPassword.getText().toString());
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
        finish();
    }

    @Override
    public void onResponse(SimpResp<LoginResultEntity> resp) {
        if (resp.isSuccess()) {
            UserEntity user = resp.getRecord().getUserInfo();

            // login to rongcloud
            mPresenter.requestRongSignIn(user.getId(), user.getNickname(), ImageUtil.head(user.getHeadimg(), user.getId(), "C"));
        } else {
            showMessage("Account or password is incorrect");
        }
    }

    @Override
    public void onRongResponse(RongLoginResp resp) {
        // get token, connect to RongIM
        if (resp.getCode() == 200) {
            if (!TextUtils.isEmpty(resp.getResult().getToken())) {
                SPUtil.setToken(getApplicationContext(), resp.getResult().getToken());
                RongIM.connect(resp.getResult().getToken(), new RongIMClient.ConnectCallback() {
                    @Override
                    public void onTokenIncorrect() {

                    }

                    @Override
                    public void onSuccess(String s) {
                        killMyself();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        } else {
            showMessage("Get Token failed");
        }
    }
}
