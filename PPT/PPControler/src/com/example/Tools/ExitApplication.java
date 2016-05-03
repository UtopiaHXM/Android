package com.example.Tools;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;




/*ʹ�÷�����
 * ��ÿ��Activity��onCreate�����е��� ExitApplication.getInstance().addActivity(this);��
 * Ȼ���ڴ����˳�������ֱ�ӵ��� ExitApplication.getInstance().exit(this);���ɡ�

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

