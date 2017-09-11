package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.crazyjiang.crazydemo.di.module.InboxModule;

import com.crazyjiang.crazydemo.mvp.ui.fragment.InboxFragment;

@ActivityScope
@Component(modules = InboxModule.class, dependencies = AppComponent.class)
public interface InboxComponent {
    void inject(InboxFragment fragment);
}