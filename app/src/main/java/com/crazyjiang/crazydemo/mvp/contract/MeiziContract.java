package com.crazyjiang.crazydemo.mvp.contract;

import com.crazyjiang.crazydemo.mvp.model.entity.DaoGankEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

public interface MeiziContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void startLoadMore();

        void endLoadMore();

        void setAdapter(List<DaoGankEntity> entity);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        List<DaoGankEntity> getEntity();
    }
}
