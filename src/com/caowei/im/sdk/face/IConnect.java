package com.caowei.im.sdk.face;

/**
 * 连接状态
 */
public interface IConnect {
    //连接成功
    void onConnected();
    //连接中断
    void onDisconnect();
    //连接失败
    void onConnectFailed();
}
