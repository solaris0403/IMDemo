package com.caowei.im.sdk.netty;

import com.caowei.im.sdk.message.IMSMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyReadHandler extends ChannelInboundHandlerAdapter {
    private final NettyClient nettyClient;

    public NettyReadHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.nettyClient.onConnected();
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        nettyClient.onMsgReceived((IMSMsg) msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.nettyClient.onDisconnect();
        super.channelInactive(ctx);
    }
}
