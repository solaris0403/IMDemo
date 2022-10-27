package com.im.client;

import com.im.InetAddress;
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
import io.netty.util.CharsetUtil;
import io.netty.util.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NettyClient {
    private String ip;
    private int port;
    public NettyClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    private void action() throws InterruptedException, IOException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 处理来自服务端的响应信息
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        // 客户端开启
        ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
        String reqStr = "我是客户端请求1$_";

        // 发送客户端的请求
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(reqStr.getBytes(CharsetUtil.UTF_8)));

        byte[] buffer = new byte[1024];
        int n;
        while ((n = System.in.read(buffer)) > 0) {
//            out.write(buffer, 0, n);
            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer(buffer, 0, n));
        }
        // 等待直到连接中断
        channelFuture.channel().closeFuture().sync();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new NettyClient("127.0.0.1", InetAddress.PORT).action();
    }
}
