package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.crazyjiang.crazydemo.di.module.CollectModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.CollectFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CollectModule.class, dependencies = AppComponent.class)
public interface CollectComponent {
    void inject(CollectFragment fragment);
}