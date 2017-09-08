package com.crazyjiang.crazydemo.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.mvp.model.entity.DaoGankEntity;
import com.crazyjiang.crazydemo.mvp.ui.holder.WelfareHolder;

import java.util.List;

/**
 * Created by zhy on 17-7-20.
 */

public class MeiziAdapter extends BaseQuickAdapter<DaoGankEntity,WelfareHolder> {

    public MeiziAdapter(@Nullable List<DaoGankEntity> data) {
        super(R.layout.girls_item, data);
    }

    @Override
    protected void convert(WelfareHolder helper, DaoGankEntity item) {
        ImageView view = helper.getView(R.id.network_imageview);
        Glide.with(helper.mAppComponent.appManager().getCurrentActivity() == null
                ? helper.mAppComponent.application() : helper.mAppComponent.appManager().getCurrentActivity())
                .load(item.url)
                .into(view);
    }


}
