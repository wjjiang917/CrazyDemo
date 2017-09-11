package com.crazyjiang.crazydemo.mvp.contract;

import com.crazyjiang.crazydemo.mvp.model.entity.QueryResp;
import com.crazyjiang.crazydemo.mvp.model.entity.RankLabelEntity;
import com.crazyjiang.crazydemo.mvp.model.entity.RoomEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;

public interface PostersContract {
    // 对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onDataLoaded(RankLabelEntity<RoomEntity> rooms);
    }

    // Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<QueryResp<RankLabelEntity<RoomEntity>>> getPopularTalents(int start, int offset);
    }
}
