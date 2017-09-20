package com.crazyjiang.crazydemo.di.component;

import com.crazyjiang.crazydemo.di.module.InboxModule;
import com.crazyjiang.crazydemo.mvp.ui.fragment.InboxFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = InboxModule.class, dependencies = AppComponent.class)
public interface InboxComponent {
    void inject(InboxFragment fragment);
}