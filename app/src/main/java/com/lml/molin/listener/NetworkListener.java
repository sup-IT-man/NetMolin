package com.lml.molin.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lml.molin.R;
import com.lml.molin.constant.SignalThreshold;
import com.lml.molin.manager.ViewManager;
import com.lml.molin.net.NetworkConfiguration;
import com.lml.molin.net.NetworkType;
import com.lml.molin.net.NetworkUtil;
import com.lml.molin.tag.TAG;
import com.lml.molin.util.Toasty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/11/20  10:47
 * 作用: 网络状态监听器
 */
public class NetworkListener extends BroadcastReceiver implements SingalChangeListener {
    private static final String WEAKSIGNAL = "当前网络信号弱";
    /**
     * 用来缓存添加与移除的View
     */
    public Map<String,View> cacheViews = new HashMap<>();
    /**
     * 记录头部View是否已经添加
     */
    public boolean hasAdd = false;
    /**
     * 配置头部view以及toast是否显示等信息
     */
    private NetworkConfiguration configuration;
    /**
     * 记录网络类型
     */
    private NetworkType type;
    private Context context;
    public NetworkListener(NetworkConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        View view = cacheViews.get("cacheView");
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.error_tips,null);
        }
        if(NetworkUtil.isNetworkAvailable(context)){
            if(intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION){
                type = NetworkUtil.getNetType(context);
                NetworkUtil.getNetLevel(context,this);
            }else if(intent.getAction() == WifiManager.RSSI_CHANGED_ACTION){
                type = NetworkUtil.getNetType(context);
                NetworkUtil.getNetLevel(context,this);
            }
            if(configuration.isShowTips()){
                if(hasAdd){
                    ViewManager.newInstance(context).removeView(context,view);
                    cacheViews.remove(view);
                    hasAdd = false;
                }
            }
        }else{
            if(configuration.isShowTips()){
                if(!hasAdd){
                    ViewManager.newInstance(context).addView(context,view);
                    cacheViews.put("cacheView",view);
                    hasAdd = true;
                }
            }
        }
    }

    @Override
    public void singalChange(int level) {
        if(NetworkType.WIFI == type){
            if(level < SignalThreshold.WifiThreshold){
                if(configuration.isToast()){
                    Toasty.error(context,WEAKSIGNAL);
                }
            }
        }else{
            if(level < SignalThreshold.SIMThreshold){
                if(configuration.isToast()){
                    Toasty.error(context,WEAKSIGNAL);
                }
            }
        }
    }
}
