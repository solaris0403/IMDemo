package com.im.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
    private Socket mSocket;

    public EchoClient(String host, int port) throws IOException {
        // 创建 socket 并连接服务器
        mSocket = new Socket(host, port);
    }

    private void readResponse() {
        try {
            InputStream in = mSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) > 0) {
                System.out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        //开一条线程读取服务返回数据
        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                readResponse();
            }
        });
        readerThread.start();
        //循环接收服务台信息，发送出去
        OutputStream out = mSocket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = System.in.read(buffer)) > 0) {
            out.write(buffer, 0, n);
        }
    }

    public static void main(String[] args) {
        try {
            // 由于服务端运行在同一主机，这里我们使用 localhost
            EchoClient client = new EchoClient("localhost", 9877);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
