package com.lml.molin.net;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.lml.molin.listener.NetworkListener;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/11/20  10:57
 * 作用: framework主类
 */
public class NetMolin {
    private NetworkListener networkListener;
    private NetworkConfiguration configuration;

    private static final NetMolin ourInstance = new NetMolin();

    public static NetMolin getInstance() {
        return ourInstance;
    }

    private NetMolin() {
        configuration = new NetworkConfiguration.Builder().build();
    }

    /**
     * 注册网络监听器
     *
     * @param context
     */
    public void register(Context context) {
        /* <action android:name="android.NET.conn.CONNECTIVITY_CHANGE" />
       <action android:name="android.Net.wifi.WIFI_STATE_CHANGED" />
       <action android:name="android.net.wifi.STATE_CHANGE" />*/

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        networkListener = new NetworkListener(configuration);
        context.registerReceiver(networkListener,intentFilter);
    }

    /**
     * 设置配置信息
     * @param configuration
     * @return
     */
    public NetMolin setConfiguration(NetworkConfiguration configuration){
        this.configuration = configuration;
        return this;
    }


    /**
     * 取消注册
     * @param context
     */
    public void unRegister(Context context){
        context.unregisterReceiver(networkListener);
    }
}
