package com.crazyjiang.crazydemo.di.component;

import com.crazyjiang.crazydemo.di.module.PostersModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.PostersFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = PostersModule.class, dependencies = AppComponent.class)
public interface PostersComponent {
    void inject(PostersFragment fragment);
}