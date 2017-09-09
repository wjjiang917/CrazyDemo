package com.crazyjiang.crazydemo.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        ImageView view = helper.getView(R.id.iv_video_image);
        Glide.with(helper.mAppComponent.appManager().getCurrentActivity() == null
                ? helper.mAppComponent.application() : helper.mAppComponent.appManager().getCurrentActivity())
                .load(item.getStandardImages())
                .into(view);

        ((TextView) helper.getView(R.id.tv_video_nickname)).setText(item.getNickName());

        TextView tvCaption = helper.getView(R.id.tv_video_caption);
        if (TextUtils.isEmpty(item.getCaption())) {
            tvCaption.setVisibility(View.GONE);
        } else {
            tvCaption.setVisibility(View.VISIBLE);
            tvCaption.setText(item.getCaption());
        }
    }
}
