package com.caowei.im.sdk.netty;

import com.caowei.im.sdk.heartbeat.HeartbeatScheduler;
import com.caowei.im.sdk.message.MessageDecoder;
import com.caowei.im.sdk.message.MessageEncoder;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 客户端过滤器，如编解码和心跳的设置
 */
public class NettyChannelHandler extends ChannelInitializer<Channel> {
    private final NettyClient nettyClient;

    public NettyChannelHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // netty提供的自定义长度解码器，解决TCP拆包/粘包问题
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

        // 编解码
        pipeline.addLast(new MessageDecoder());
        pipeline.addLast(new MessageEncoder());

        //心跳
//        pipeline.addLast(new IdleStateHandler(0, 0, 270, TimeUnit.SECONDS));

        // 接收消息处理handler
        pipeline.addLast(new NettyReadHandler(nettyClient));
        //重试策略
//        pipeline.addLast(new NettyRetryHandler(nettyClient));
    }
}
