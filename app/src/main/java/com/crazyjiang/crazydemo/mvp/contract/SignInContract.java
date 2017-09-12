package com.crazyjiang.crazydemo.mvp.contract;

import com.crazyjiang.crazydemo.mvp.model.entity.LoginResultEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RongLoginResp;
import com.crazyjiang.crazydemo.mvp.model.entity.SimpResp;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;

public interface SignInContract {
    // 对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onResponse(SimpResp<LoginResultEntity> resp);

        void onRongResponse(RongLoginResp resp);
    }

    // Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<SimpResp<LoginResultEntity>> signIn(String account, String password, String imei);

        Observable<RongLoginResp> signInRong(Integer userId, String name, String image);
    }
}
