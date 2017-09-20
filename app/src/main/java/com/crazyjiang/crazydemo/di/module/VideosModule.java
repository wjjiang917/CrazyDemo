package com.crazyjiang.crazydemo.di.module;

import com.crazyjiang.crazydemo.mvp.contract.VideosContract;
import com.crazyjiang.crazydemo.mvp.model.VideosModel;
import com.crazyjiang.crazydemo.mvp.ui.fragment.VideosFragment;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class VideosModule {
    private VideosContract.View view;
    private VideosFragment fragment;

    /**
     * 构建VideosModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public VideosModule(VideosContract.View view, VideosFragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    @Singleton
    @Provides
    VideosFragment provideFragment() {
        return fragment;
    }

    @FragmentScope
    @Provides
    VideosContract.View provideVideosView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    VideosContract.Model provideVideosModel(VideosModel model) {
        return model;
    }
}