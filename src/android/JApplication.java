package com.orange.xiaoyuan;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.util.Log;

public class JApplication extends Application {
    private static final String TAG = "JApplication";

    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "onCreate");
         super.onCreate();
         JPushInterface.setDebugMode(true);
         JPushInterface.init(this);
    }
}
