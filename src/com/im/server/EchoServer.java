package com.im.server;

import com.im.InetAddress;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class EchoServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(InetAddress.PORT);
            System.out.println("开始监听端口：" + InetAddress.PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + " 已连接");
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                byte[] buffer = new byte[1024];
                int offset;
                while ((offset = in.read(buffer)) > 0) {
                    //读取到消息字节，用当前socket的流发送回去
                    out.write(buffer, 0, offset);
                }
                System.out.println("断开连接");
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server: Error");
        }
    }
}
