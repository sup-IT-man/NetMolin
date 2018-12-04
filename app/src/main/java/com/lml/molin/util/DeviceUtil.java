package com.lml.molin.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/11/28  10:15
 * 作用: 获取设备相关信息
 */
public class DeviceUtil {

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕的宽
     * @param activity
     * @return
     */
    public  static int getWidth(Activity activity){
        Display display=activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return  displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高
     * @param activity
     * @return
     */
    public  static int getHeight(Activity activity){
        Display display=activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return  displayMetrics.heightPixels;
    }
}
