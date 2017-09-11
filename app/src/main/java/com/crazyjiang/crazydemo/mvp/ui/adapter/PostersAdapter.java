package com.crazyjiang.crazydemo.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.utils.ImageUtil;
import com.crazyjiang.crazydemo.mvp.model.entity.RoomEntity;
import com.crazyjiang.crazydemo.mvp.ui.holder.CommonHolder;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;

import java.util.List;

public class PostersAdapter extends BaseQuickAdapter<RoomEntity, CommonHolder> {
    public PostersAdapter(@Nullable List<RoomEntity> data) {
        super(R.layout.item_posters, data);
    }

    @Override
    protected void convert(CommonHolder helper, RoomEntity item) {
        String image = ImageUtil.talent(item.getRoomimg(), item.getId(), "D");
        if (TextUtils.isEmpty(item.getRoomimg())) {
            image = ImageUtil.head(item.getHeadimg(), item.getId(), "D");
        }

        helper.mImageLoader.loadImage(helper.mAppComponent.appManager().getCurrentActivity() == null
                        ? helper.mAppComponent.application() : helper.mAppComponent.appManager().getCurrentActivity(),
                ImageConfigImpl
                        .builder()
                        .url(image)
                        .imageView(helper.getView(R.id.ivImage))
                        .build());
    }
}
