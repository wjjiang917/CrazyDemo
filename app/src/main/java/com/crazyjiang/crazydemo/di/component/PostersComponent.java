package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.crazyjiang.crazydemo.di.module.PostersModule;

import com.crazyjiang.crazydemo.mvp.ui.fragment.PostersFragment;

@ActivityScope
@Component(modules = PostersModule.class, dependencies = AppComponent.class)
public interface PostersComponent {
    void inject(PostersFragment fragment);
}