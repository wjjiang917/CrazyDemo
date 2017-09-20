package com.crazyjiang.crazydemo.di.module;

import com.crazyjiang.crazydemo.mvp.contract.PostersContract;
import com.crazyjiang.crazydemo.mvp.model.PostersModel;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class PostersModule {
    private PostersContract.View view;

    /**
     * 构建PostersModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PostersModule(PostersContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    PostersContract.View providePostersView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    PostersContract.Model providePostersModel(PostersModel model) {
        return model;
    }
}