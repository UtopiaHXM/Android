package com.example.Tools;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;




/*使用方法：
 * 在每个Activity的onCreate方法中调用 ExitApplication.getInstance().addActivity(this);，
 * 然后在处理退出请求中直接调用 ExitApplication.getInstance().exit(this);即可。

 * */



public class ExitApplication extends Application {

    private List<Activity> list = new ArrayList<Activity>();
 
    private static ExitApplication ea;

    private ExitApplication() {

    }

    public static ExitApplication getInstance() {
        if (null == ea) {
            ea = new ExitApplication();
        }
        return ea;
    }

   
    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exit(Context context) {
        for (Activity activity : list) {
            activity.finish();
        }
        System.exit(0);
    }
}

