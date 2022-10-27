package com.caowei.im;

import com.im.InetAddress;
import com.im.client.ClientHandler;
import com.im.client.NettyClient;
import com.im.server.EchoServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        // 处理来自服务端的响应信息
                        //1.拆包器
                        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 5, 4));
                        //2.自定义解码器
                        socketChannel.pipeline().addLast(new MyDecoder());
                        //3.自定义业务
                        socketChannel.pipeline().addLast(new ClientChatHandler());
                        //4.自定义编码器
                        socketChannel.pipeline().addLast(new MyEncoder());
                    }
                });
        // 客户端开启
        ChannelFuture channelFuture = bootstrap.connect(InetAddress.IP, InetAddress.PORT).sync();
        // 等待直到连接中断
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
    }
}
