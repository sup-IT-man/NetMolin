package com.lml.molin.constant;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/12/4  14:10
 * 作用: wifi信号弱的阈值与SIM网络信号阈值
 */
public class SignalThreshold {
    /**
     * wifi的环境下如果信号阈值低于4000，那么就是信号弱
     */
    public static  int WifiThreshold = 4000;
    /**
     * SIM的环境下，如果信号阈值低于2，就是信号弱
     */
    public static  int SIMThreshold = 2;
}
