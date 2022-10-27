package com.caowei.im.sdk;

import com.caowei.im.sdk.face.IMSClientInterface;
import com.caowei.im.sdk.netty.NettyClient;

/**
 * ims实例工厂方法
 */
public class IMSClientFactory {
    public static IMSClientInterface getIMSClient() {
        return NettyClient.getInstance();
    }
}
