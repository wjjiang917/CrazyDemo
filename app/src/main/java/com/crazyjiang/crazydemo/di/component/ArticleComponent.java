package com.crazyjiang.crazydemo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.crazyjiang.crazydemo.di.module.ArticleModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.ArticleFragment;

import dagger.Component;

@ActivityScope
@Component(modules = ArticleModule.class, dependencies = AppComponent.class)
public interface ArticleComponent {
    void inject(ArticleFragment fragment);
}