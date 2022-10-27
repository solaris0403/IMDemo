package com.caowei.im;

import com.im.InetAddress;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        // 用来接收进来的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 用来处理已经被接收的连接，一旦bossGroup接收到连接，就会把连接信息注册到workerGroup上
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // nio服务的启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class); // 说明一个新的Channel如何接收进来的连接
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        System.out.println("initChannel");
                        //1.拆包器
                        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,5,4));
                        //2.自定义解码器
                        socketChannel.pipeline().addLast(new MyDecoder());
                        //3.业务Handler
                        socketChannel.pipeline().addLast(new ServerChatHandler());
                        //4.自定义编码器
                        socketChannel.pipeline().addLast(new MyEncoder());
                    }
                });
        //设置TCP参数
        //1.链接缓冲池的大小（ServerSocketChannel的设置）
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        //维持链接的活跃，清除死链接(SocketChannel的设置)
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        //关闭延迟发送
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        // 绑定端口，开始接受链接
        ChannelFuture future = bootstrap.bind(InetAddress.PORT).sync();
        System.out.println("server start ...... ");
        // 等待服务端口的关闭；在这个例子中不会发生，但你可以优雅实现；关闭你的服务
//        future.channel().closeFuture().sync();
//        bossGroup.shutdownGracefully();
//        workerGroup.shutdownGracefully();
    }
}
