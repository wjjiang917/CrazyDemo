package com.crazyjiang.crazydemo.di.component;

import com.crazyjiang.crazydemo.di.module.MainModule;
import com.crazyjiang.crazydemo.mvp.ui.activity.MainActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}