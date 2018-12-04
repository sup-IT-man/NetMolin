package com.lml.molin.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/11/26  10:47
 * 作用: Toast提示
 */
public class Toasty {
    private static Toast toast;

    public static void error(Context context,String msg){
        if(toast == null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
