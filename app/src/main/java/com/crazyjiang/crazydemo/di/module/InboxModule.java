package com.crazyjiang.crazydemo.di.module;

import com.crazyjiang.crazydemo.mvp.contract.InboxContract;
import com.crazyjiang.crazydemo.mvp.model.InboxModel;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class InboxModule {
    private InboxContract.View view;

    /**
     * 构建InboxModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public InboxModule(InboxContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    InboxContract.View provideInboxView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    InboxContract.Model provideInboxModel(InboxModel model) {
        return model;
    }
}