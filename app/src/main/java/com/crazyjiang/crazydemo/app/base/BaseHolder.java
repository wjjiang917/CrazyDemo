package com.crazyjiang.crazydemo.app.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.ThirdViewUtil;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.Unbinder;

/**
 * Created by Jiangwenjin on 2017/9/13.
 */
public class BaseHolder extends BaseViewHolder {
    public AppComponent mAppComponent;
    public ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    private Unbinder mUnbinder;

    public BaseHolder(View view) {
        super(view);

        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();

        // autolayout
        if (ThirdViewUtil.USE_AUTOLAYOUT == 1) {
            AutoUtils.autoSize(itemView);
        }

        // ButterKnife
        mUnbinder = ThirdViewUtil.bindTarget(this, itemView);
//        mUnbinder = ButterKnife.bind(itemView);
    }

    private void onRelease() {
        mImageLoader = null;
        mAppComponent = null;

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        mUnbinder = null;
    }

    public static void releaseHolders(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
            final View view = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder != null && viewHolder instanceof BaseHolder) {
                ((BaseHolder) viewHolder).onRelease();
            }
        }
    }
}
