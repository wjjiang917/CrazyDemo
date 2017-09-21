package com.crazyjiang.crazydemo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.base.BaseFragment;
import com.crazyjiang.crazydemo.app.base.BaseHolder;
import com.crazyjiang.crazydemo.di.component.DaggerVideosComponent;
import com.crazyjiang.crazydemo.di.module.VideosModule;
import com.crazyjiang.crazydemo.mvp.contract.VideosContract;
import com.crazyjiang.crazydemo.mvp.model.entity.VideoEntity;
import com.crazyjiang.crazydemo.mvp.presenter.VideosPresenter;
import com.crazyjiang.crazydemo.mvp.ui.adapter.VideosAdapter;
import com.crazyjiang.crazydemo.mvp.ui.widget.RefreshHeaderView;
import com.crazyjiang.library.easyrefreshlayout.EasyRefreshLayout;
import com.crazyjiang.library.easyrefreshlayout.LoadModel;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;

import static com.crazyjiang.crazydemo.R.id.recyclerView;
import static com.jess.arms.utils.Preconditions.checkNotNull;

public class VideosFragment extends BaseFragment<VideosPresenter> implements VideosContract.View {
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    EasyRefreshLayout mRefreshLayout;

    private VideosAdapter mAdapter;
    private boolean loadMore = false;

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerVideosComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .videosModule(new VideosModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        ArmsUtils.configRecycleView(mRecyclerView, layoutManager);
        mAdapter = new VideosAdapter(null);

        TextView textView = new TextView(getContext());
        textView.setText("No More Data.");
        textView.setGravity(Gravity.CENTER);
        mAdapter.setEmptyView(textView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();  //防止第一行到顶部有空白区域
            }
        });

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
//        mAdapter.setOnLoadMoreListener(() -> {
//            loadMore = true;
//            mPresenter.requestData(true);
//        }, mRecyclerView);
//        mAdapter.disableLoadMoreIfNotFullPage();

        mRefreshLayout.setRefreshHeadView(new RefreshHeaderView(getContext()));
        mRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                loadMore = true;
                mPresenter.requestData(true);
            }

            @Override
            public void onRefreshing() {
                loadMore = false;
                mPresenter.requestData(false);
            }
        });
    }

    @Override
    public void onFirstVisible() {
        //去服务器下载数据
        loadMore = false;
        mPresenter.requestData(false);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

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

    }

    @Override
    public void startLoadMore() {
        //mAdapter.setEnableLoadMore(true);
    }

    @Override
    public void endLoadMore() {
        mRefreshLayout.closeLoadView();
    }

    @Override
    public void showLoading() {
//        Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.refreshComplete();
//        Observable.just(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void onVideosLoaded(List<VideoEntity> mData) {
        if (mData.size() < VideosPresenter.PAGE_SIZE) {
            // mAdapter.loadMoreEnd();  // 数据全部加载完毕

            mRefreshLayout.setLoadMoreModel(LoadModel.NONE);
        }

        if (loadMore) {
            mAdapter.addData(mData);
        } else {
            mAdapter.setNewData(mData);
        }
    }

    @Override
    public void onDestroy() {
        BaseHolder.releaseHolders(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
    }
}
