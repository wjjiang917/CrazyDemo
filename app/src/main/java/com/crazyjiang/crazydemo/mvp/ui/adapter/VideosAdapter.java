package com.crazyjiang.crazydemo.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.base.BaseHolder;
import com.crazyjiang.crazydemo.mvp.model.entity.VideoEntity;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

import io.reactivex.Observable;

public class VideosAdapter extends BaseQuickAdapter<VideoEntity, BaseHolder> {
    public VideosAdapter(@Nullable List<VideoEntity> data) {
        super(R.layout.item_videos, data);
    }

    @Override
    protected void convert(BaseHolder helper, VideoEntity item) {
        helper.mImageLoader.loadImage(helper.mAppComponent.appManager().getTopActivity() == null
                        ? helper.mAppComponent.application() : helper.mAppComponent.appManager().getTopActivity(),
                ImageConfigImpl.builder()
                        .url(item.getStandardImages())
                        .imageView(helper.getView(R.id.iv_video_poster))
                        .build());

        Observable.just(item.getNickName())
                .subscribe(s -> helper.setText(R.id.tv_video_nickname, s));

        if (TextUtils.isEmpty(item.getCaption())) {
            helper.setVisible(R.id.tv_video_caption, false);
        } else {
            helper.setVisible(R.id.tv_video_caption, true);

            Observable.just(item.getCaption()).subscribe(s -> helper.setText(R.id.tv_video_caption, s));
        }
    }
}
