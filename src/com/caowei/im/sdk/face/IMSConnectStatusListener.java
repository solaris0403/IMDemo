package com.caowei.im.sdk.face;

/**
 * IMS连接状态监听器
 */
public interface IMSConnectStatusListener {
    void onConnecting();
    void onConnected();
    void onConnectFailed();
    void onDisconnect();
}
