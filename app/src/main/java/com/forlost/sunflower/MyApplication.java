package com.forlost.sunflower;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.forlost.sunflower.helper.ApplicationHelper;

import java.util.List;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //所有的全局方法都放到ApplicationHelper
        if (isMainProcess()) {
            ApplicationHelper.getInstance().init(this);
        }

    }

    private boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        if (am == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
