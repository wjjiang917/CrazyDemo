package com.crazyjiang.crazydemo.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.mvp.model.entity.Video;
import com.crazyjiang.crazydemo.mvp.ui.holder.CommonHolder;

import java.util.List;

public class VideosAdapter extends BaseQuickAdapter<Video, CommonHolder> {
    public VideosAdapter(@Nullable List<Video> data) {
        super(R.layout.item_videos, data);
    }

    @Override
    protected void convert(CommonHolder helper, Video item) {
        ImageView view = helper.getView(R.id.network_imageview);
        Glide.with(helper.mAppComponent.appManager().getCurrentActivity() == null
                ? helper.mAppComponent.application() : helper.mAppComponent.appManager().getCurrentActivity())
                .load(item.getStandardImages())
                .into(view);
    }
}
