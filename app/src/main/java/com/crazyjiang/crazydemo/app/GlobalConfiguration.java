package com.crazyjiang.crazydemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.crazyjiang.crazydemo.BuildConfig;
import com.crazyjiang.crazydemo.R;
import com.crazyjiang.crazydemo.app.constant.Constant;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMManager;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import java.util.List;

import timber.log.Timber;

/**
 * app的全局配置信息在此配置,需要将此实现类声明到AndroidManifest中
 * Created by jess on 12/04/2017 17:25
 * Contact with jess.yan.effort@gmail.com
 */
public class GlobalConfiguration implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        // 使用builder可以为框架配置一些配置信息
        builder.baseurl(Constant.SERVER_IP);

        // 用来提供处理所有错误的监听
        // rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),此监听才生效
        builder.responseErrorListener((context1, t) -> {
            Timber.e(t.getMessage());
            ArmsUtils.snackbarText("Network error");
        });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        //AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {
            @Override
            public void attachBaseContext(Context base) {
            }

            @Override
            public void onCreate(Application application) {
                if (BuildConfig.LOG_DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                }
                //leakCanary内存泄露检查
                ((App) application).getAppComponent().extras().put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);

                //ARouter初始化
                if (BuildConfig.LOG_DEBUG) {
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                }
                ARouter.init(application); // 尽可能早，推荐在Application中初始化
                GreenDaoHelper.initDatabase(application);


                // tencent IM
                if (MsfSdkUtils.isMainProcess(application)) {
                    TIMManager.getInstance().setOfflinePushListener(notification -> {
                        if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                            //消息被设置为需要提醒
                            notification.doNotify(application, R.mipmap.ic_launcher);
                        }
                    });
                }
            }

            @Override
            public void onTerminate(Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //向Activity的生命周期中注入一些自定义逻辑
        lifecycles.add(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Timber.w(activity + " - onActivityCreated");
                //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
                if (activity.findViewById(R.id.toolbar) != null) {
                    if (activity instanceof AppCompatActivity) {
                        ((AppCompatActivity) activity).setSupportActionBar(activity.findViewById(R.id.toolbar));
                        ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setActionBar(activity.findViewById(R.id.toolbar));
                            activity.getActionBar().setDisplayShowTitleEnabled(false);
                        }
                    }
                }
                if (activity.findViewById(R.id.toolbar_title) != null) {
                    ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                }
                if (activity.findViewById(R.id.toolbar_back) != null) {
                    activity.findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                        activity.onBackPressed();
                    });
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //向Fragment的生命周期中注入一些自定义逻辑
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                ((RefWatcher) ((App) f.getActivity().getApplication()).getAppComponent().extras().get(RefWatcher.class.getName())).watch(f);
            }
        });
    }
}
