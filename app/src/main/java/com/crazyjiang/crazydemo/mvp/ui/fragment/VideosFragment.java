package com.crazyjiang.crazydemo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.crazyjiang.crazydemo.di.component.DaggerVideosComponent;
import com.crazyjiang.crazydemo.di.module.VideosModule;
import com.crazyjiang.crazydemo.mvp.contract.VideosContract;
import com.crazyjiang.crazydemo.mvp.model.entity.Video;
import com.crazyjiang.crazydemo.mvp.presenter.VideosPresenter;
import com.crazyjiang.crazydemo.mvp.ui.adapter.VideosAdapter;
import com.crazyjiang.crazydemo.mvp.ui.widget.SpacesItemDecoration;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.crazyjiang.crazydemo.R.id.recyclerView;
import static com.jess.arms.utils.Preconditions.checkNotNull;

public class VideosFragment extends BaseFragment<VideosPresenter> implements VideosContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private VideosAdapter mAdapter;

    public static VideosFragment newInstance() {
        return new VideosFragment();
    }

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
        return inflater.inflate(R.layout.layout_refresh_list, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecycleView(mRecyclerView, new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new VideosAdapter(null);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });

        TextView textView = new TextView(getContext());
        textView.setText("No More Data.");
        textView.setGravity(Gravity.CENTER);
        mAdapter.setEmptyView(textView);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onFragmentFirstVisible() {
        //去服务器下载数据
        mPresenter.requestData(true);
    }

    @Override
    public void startLoadMore() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void endLoadMore() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Subscriber(tag = "meizi")
    private void updateAdapter(Object o) {
        mPresenter.requestData(true);
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void onRefresh() {
        mPresenter.requestData(true);
    }

    @Override
    public void setNewData(List<Video> mData) {
        mAdapter.setNewData(mData);
    }

    @Override
    public void setAddData(List<Video> results) {
        mAdapter.addData(results);
    }
}
