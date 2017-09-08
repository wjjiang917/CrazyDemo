package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.crazyjiang.crazydemo.di.module.WelfareModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.WelfareFragment;

import dagger.Component;

@ActivityScope
@Component(modules = WelfareModule.class, dependencies = AppComponent.class)
public interface WelfareComponent {
    void inject(WelfareFragment fragment);
}