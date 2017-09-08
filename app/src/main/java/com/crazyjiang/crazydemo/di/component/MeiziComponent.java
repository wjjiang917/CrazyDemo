package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.crazyjiang.crazydemo.di.module.MeiziModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.MeiziFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MeiziModule.class, dependencies = AppComponent.class)
public interface MeiziComponent {
    void inject(MeiziFragment fragment);
}