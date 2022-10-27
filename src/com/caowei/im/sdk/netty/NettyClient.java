package com.caowei.im.sdk.netty;

import com.caowei.im.sdk.config.IMSConfig;
import com.caowei.im.sdk.face.IConnect;
import com.caowei.im.sdk.face.IMSClientInterface;
import com.caowei.im.sdk.face.IMSConnectStatusListener;
import com.caowei.im.sdk.face.OnEventListener;
import com.caowei.im.sdk.message.IMSMsg;
import com.caowei.im.sdk.message.IMSMsgReceivedListener;
import com.caowei.im.sdk.retry.DefaultRetryPolicy;
import com.caowei.im.sdk.retry.IRetryPolicy;
import com.caowei.im.sdk.utils.LogUtils;
import com.im.InetAddress;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于netty实现的tcp ims
 */
public class NettyClient implements IMSClientInterface, IMSMsgReceivedListener, IConnect {
    private static volatile NettyClient instance;

    private NettyClient() {
    }

    public static NettyClient getInstance() {
        if (null == instance) {
            synchronized (NettyClient.class) {
                if (null == instance) {
                    instance = new NettyClient();
                }
            }
        }
        return instance;
    }

    private boolean isClosed = true;// ims是否已关闭
    private boolean isDisconnect = true;// ims是否已断开
    private volatile Channel mChannel;
    private List<InetAddress> serverUrlList;// ims服务器地址组
    private OnEventListener mOnEventListener;// 与应用层交互的listener
    private IMSConnectStatusListener mIMSConnectStatusListener;// ims连接状态回调监听器
    private ExecutorService executors;
    private DefaultRetryPolicy mRetryPolicy;

    @Override
    public void init(List<InetAddress> serverUrlList, OnEventListener listener, IMSConnectStatusListener callback) {
        this.serverUrlList = serverUrlList;
        this.mOnEventListener = listener;
        this.mIMSConnectStatusListener = callback;
        this.executors = Executors.newCachedThreadPool();
        this.mRetryPolicy = new DefaultRetryPolicy(new WeakReference<>(this));
        reconnect(true);
    }

    @Override
    public void connect() {
        executors.submit(new Runnable() {
            @Override
            public void run() {
                connectServers();
            }
        });
    }

    @Override
    public void reconnect(boolean initial) {
        executors.submit(new Runnable() {
            @Override
            public void run() {
                connectServers();
            }
        });
    }

    /**
     * 遍历连接多个服务器
     */
    private void connectServers() {
        // 重连时，停止心跳
        // 网络可用才进行连接
        for (InetAddress inetAddress : serverUrlList) {
            realConnectServer(inetAddress.getIp(), inetAddress.getPort());
        }
    }

    private void realConnectServer(String ip, int port) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        // 设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // 设置禁用nagle算法
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 设置连接超时时长
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, IMSConfig.CONNECT_TIMEOUT);
        // 设置连接Channel
        bootstrap.handler(new NettyChannelHandler(this));
        try {
            LogUtils.log("准备连接:" + ip + "/" + port);
            mChannel = bootstrap.connect(ip, port).sync().channel();
            mChannel.closeFuture().sync();
        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtils.log(e.getMessage());
            this.onConnectFailed();
        } finally {
            if (mChannel != null) {
                mChannel.close();
                mChannel = null;
            }
            loopGroup.shutdownGracefully();
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public boolean isDisconnect() {
        return isDisconnect;
    }

    @Override
    public void sendMessage(IMSMsg message) {
        if (mChannel != null) {
            mChannel.writeAndFlush(message);
        }
    }

    @Override
    public void onMsgReceived(IMSMsg msg) {
        LogUtils.log("客户端收到的消息:" + (msg).getContent());
    }

    @Override
    public void onConnected() {
        //已经连接
        LogUtils.log("onConnected");
//        mRetryPolicy.stopRetry();
    }

    @Override
    public void onDisconnect() {
        //断开连接
        LogUtils.log("onDisconnect");
//        mRetryPolicy.startRetry();
    }

    @Override
    public void onConnectFailed() {
        LogUtils.log("onConnectFailed");
//        mRetryPolicy.startRetry();
    }
}
