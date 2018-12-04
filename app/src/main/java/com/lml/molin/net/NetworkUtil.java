package com.lml.molin.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.lml.molin.listener.SingalChangeListener;
import com.lml.molin.util.Toasty;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/11/20  10:50
 * 作用: 网络状态获取工具
 */
public class NetworkUtil {
    public static final String TAG = "Network";
    /**
     * 当前机器无信号
     */
    private static final int MAX_LEVEL = 10000;
    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取网络类型
     *
     * @param context GPRS : 2G(2.5) General Packet Radia Service 114kbps
     *                EDGE : 2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     *                UMTS : 3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
     *                CDMA : 2G 电信 Code Division Multiple Access 码分多址
     *                EVDO_0 : 3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     *                EVDO_A : 3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     *                1xRTT : 2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     *                HSDPA : 3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     *                HSUPA : 3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     *                HSPA : 3G (分HSDPA,HSUPA) High Speed Packet Access
     *                IDEN : 2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     *                EVDO_B : 3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     *                LTE : 4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     *                EHRPD : 3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     *                HSPAP : 3G HSPAP 比 HSDPA 快些
     * @return
     */
    public static NetworkType getNetType(Context context) {
        NetworkType type = NetworkType.NONE;
        if (isNetworkAvailable(context)) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (null != networkInfo) {
                NetworkInfo.State state = networkInfo.getState();
                if (null != state) {
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        type = NetworkType.WIFI;
                    }
                }
            }
            //如果有wifi了，那么就不进行SIM卡的网络的处理了
            if(type == NetworkType.NONE){
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                switch (tm.getNetworkType()) {
                    // 2G网络
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        type = NetworkType.TWO;
                        break;
                    // 3G网络
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        type = NetworkType.THREE;
                        break;
                    // 4G网络
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        type = NetworkType.FOUR;
                        break;
                    default:
                        break;
                }
            }
        }
        return type;
    }

    /**
     * 获取网络信号强度
     * 如果连接wifi，则首选判断wifi的信号强度，如果没连接wifi，那么先判断机器中是否有卡，如果机器没SIM卡，那么直接返回没信号的level
     * 如果有SIM卡，那么判断当前移动网络信号强度
     */
    public static void getNetLevel(Context context, final SingalChangeListener singalChangeListener){
        int level = MAX_LEVEL;
        if(NetworkType.WIFI == getNetType(context)){
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if(connectionInfo.getBSSID() != null){
                level = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), MAX_LEVEL);
                if(singalChangeListener != null){
                    singalChangeListener.singalChange(level);
                }
            }
        }else{
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT){
                Toasty.error(context,"请插入SIM或者开启wifi");
            }else if(tm.getSimState() == TelephonyManager.SIM_STATE_READY){
                tm.listen(new PhoneStateListener(){
                    @Override
                    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                        super.onSignalStrengthsChanged(signalStrength);
                        int tempLevel = signalStrength.getGsmSignalStrength();
                        if(singalChangeListener != null){
                            singalChangeListener.singalChange(tempLevel);
                        }
                    }
                },PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            }
        }
    }
}
