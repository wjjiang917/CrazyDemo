package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.crazyjiang.crazydemo.di.module.CategoryModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.CategoryFragment;

import dagger.Component;

@ActivityScope
@Component(modules = CategoryModule.class, dependencies = AppComponent.class)
public interface CategoryComponent {
    void inject(CategoryFragment fragment);
}