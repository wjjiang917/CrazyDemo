package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.crazyjiang.crazydemo.di.module.SignInModule;

import com.crazyjiang.crazydemo.mvp.ui.activity.SignInActivity;

@ActivityScope
@Component(modules = SignInModule.class, dependencies = AppComponent.class)
public interface SignInComponent {
    void inject(SignInActivity activity);
}