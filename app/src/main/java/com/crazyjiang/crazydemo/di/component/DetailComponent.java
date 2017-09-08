package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.crazyjiang.crazydemo.di.module.DetailModule;
import com.crazyjiang.crazydemo.mvp.ui.activity.DetailActivity;

import dagger.Component;

@ActivityScope
@Component(modules = DetailModule.class, dependencies = AppComponent.class)
public interface DetailComponent {
    void inject(DetailActivity activity);
}