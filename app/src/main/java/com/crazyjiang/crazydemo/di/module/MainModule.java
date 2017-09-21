package com.crazyjiang.crazydemo.di.module;

import com.crazyjiang.crazydemo.mvp.contract.MainContract;
import com.crazyjiang.crazydemo.mvp.model.MainModel;
import com.crazyjiang.crazydemo.mvp.ui.fragment.InboxFragment;
import com.crazyjiang.crazydemo.mvp.ui.fragment.PostersFragment;
import com.crazyjiang.crazydemo.mvp.ui.fragment.VideosFragment;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private MainContract.View view;

    /**
     * 构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }

    /**
     * 当需要在Activity中通过@Inject注入Fragment时，需要将对应的Fragment在ActivityModule中@Provides
     *
     * @return
     */
    // @Singleton  // 作用域不同
    @ActivityScope
    @Provides
    VideosFragment provideVideosFragment() {
        return new VideosFragment();
    }

    @ActivityScope
    @Provides
    PostersFragment providePostersFragment() {
        return new PostersFragment();
    }

    @ActivityScope
    @Provides
    InboxFragment provideInboxFragment() {
        return new InboxFragment();
    }
}