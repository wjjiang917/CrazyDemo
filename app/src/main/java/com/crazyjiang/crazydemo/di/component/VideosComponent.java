package com.crazyjiang.crazydemo.di.component;

import com.crazyjiang.crazydemo.di.module.VideosModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.VideosFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = VideosModule.class, dependencies = AppComponent.class)
public interface VideosComponent {
    void inject(VideosFragment fragment);
}