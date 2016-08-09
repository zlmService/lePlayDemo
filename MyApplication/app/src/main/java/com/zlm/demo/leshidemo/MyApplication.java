package com.zlm.demo.leshidemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.lecloud.sdk.config.LeCloudPlayerConfig;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.List;

/**
 * Created by zhaoliming on 2016/8/8.
 */
public class MyApplication extends Application {
    private static MyApplication mContext;
    public Bus bus;

    @Override
    public void onCreate() {
        super.onCreate();

        bus = new Bus(ThreadEnforcer.ANY); //初始化otto
        mContext=this;
        String processName = getProcessName(this, android.os.Process.myPid());
//设置地域名 LeCloudPlayerConfig.HOST_DEFAULT代表国内版
        int host = LeCloudPlayerConfig.HOST_DEFAULT;
        if (getApplicationInfo().packageName.equals(processName)) {
////CrashHandler是一个抓取崩溃log的工具类（可选）
//            CrashHandler.getInstance(this);
            LeCloudPlayerConfig.setHostType(host);
//cde初始化
            LeCloudPlayerConfig.init(getApplicationContext());
        }
    }

    //获取当前进程名字
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }



    public static MyApplication getApp() {
        return mContext;
    }

    public  Bus getBus() {
        return bus;
    }

}
