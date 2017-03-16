package com.car.contractcar.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.car.contractcar.myapplication.common.http.HttpUtil;


/**
 * Created by Administrator on 2015/12/11.
 */
public class MyApplication extends Application {

    public static Context context = null;

    public static Handler handler = null;

    public static Thread mainThread = null;


    public static int mainThreadId = 0;


    @Override
    public void onCreate() {
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        new HttpUtil(this);

    }

}