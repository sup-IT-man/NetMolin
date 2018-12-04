package com.lml.molin.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.lml.molin.util.DeviceUtil;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/11/20  11:35
 * 作用: view管理者
 */
public class ViewManager {
    public static ViewManager manager = null;

    private ViewManager() {
    }

    public static ViewManager newInstance(Context context) {
        if (manager == null) {
            manager = new ViewManager();
        }
        return manager;
    }

    public void addView(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity) context).startActivityForResult(intent, 1);
            } else {
                addViewInner(context,view);
            }
        } else {
            addViewInner(context,view);
        }
    }

    private void addViewInner(Context context, View view){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        //设置添加View的类型
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置添加View的标识
        wmParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        //设置添加View的默认坐标值
        wmParams.x = 0;
        wmParams.y = DeviceUtil.getStatusBarHeight(context);
        ;
        //设置添加View的宽高
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;
        wm.addView(view, wmParams);
    }

    public void removeView(Context context, View view) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.removeView(view);
    }
}
