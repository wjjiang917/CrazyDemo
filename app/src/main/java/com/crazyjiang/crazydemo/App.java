package com.crazyjiang.crazydemo;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import timber.log.Timber;

/**
 * Created by Jiangwenjin on 2017/8/31.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_APPID, false);
    }
}
