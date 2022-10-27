package com.caowei.im.sdk.face;

import com.caowei.im.sdk.message.IMSMsg;
import com.caowei.im.sdk.retry.IRetryPolicy;
import com.im.InetAddress;

import java.util.List;

/**
 * ims抽象接口
 */
public interface IMSClientInterface {
    /**
     * 初始化
     *
     * @param serverUrlList
     * @param listener
     * @param callback
     */
    void init(List<InetAddress> serverUrlList, OnEventListener listener, IMSConnectStatusListener callback);

    /**
     * 连接
     */
    void connect();

    /**
     * 重连
     *
     * @param initial 第一次重连
     */
    void reconnect(boolean initial);

    /**
     * 关闭IMS，释放资源
     */
    void close();

    /**
     * 仅断开长连接，并不释放资源。
     * 在下次进行连接的时候，无需重新调用init()方法初始化。
     */
    void disconnect();

    /**
     * 是否关闭
     *
     * @return
     */
    boolean isClosed();

    /**
     * 是否断开
     *
     * @return
     */
    boolean isDisconnect();

    /**
     * 发送消息
     *
     * @param message
     */
    void sendMessage(IMSMsg message);
}
