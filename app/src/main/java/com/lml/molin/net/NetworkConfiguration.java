package com.lml.molin.net;

/**
 * @author lml mlluo@szrlzz.com
 * @date 2018/11/20  11:22
 * 作用: 网络配置
 */
public class NetworkConfiguration {
    private boolean isShowTips;
    private boolean isToast;
    private String tipsContent;

    NetworkConfiguration(Builder builder) {
        this.isShowTips = builder.isShowTips();
        this.isToast = builder.isToast();
        this.tipsContent = builder.getTipsContent();
    }

    public boolean isShowTips() {
        return isShowTips;
    }

    public boolean isToast() {
        return isToast;
    }

    public String getTipsContent() {
        if (tipsContent == null) {
            return "";
        } else {
            return tipsContent;
        }
    }

    public static class Builder{
        private static final String DEFAULTCONTENT = "暂无网络连接，请检查网络状态";
        private boolean isShowTips;
        private boolean isToast;
        private String tipsContent;
        public Builder(){
            this.isShowTips = true;
            this.isToast = false;
            this.tipsContent = DEFAULTCONTENT;
        }

        public Builder setIsShowTips(boolean isShowTips){
            this.isShowTips = isShowTips;
            return this;
        }

        public Builder setIsToast(boolean isToast){
            this.isToast = isToast;
            return this;
        }

        public Builder setTipsContent(String content){
            this.tipsContent = tipsContent;
            return this;
        }

        public boolean isShowTips(){
            return isShowTips;
        }

        public boolean isToast(){
            return isToast;
        }

        public String getTipsContent(){
            if(tipsContent == null){
                this.tipsContent = DEFAULTCONTENT;
            }
            return tipsContent;
        }

        public NetworkConfiguration build(){
            return new NetworkConfiguration(this);
        }
    }
}
