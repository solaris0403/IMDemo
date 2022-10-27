package com.im;

public class InetAddress {
    public static final String IP = "127.0.0.1";
    public static final int PORT = 8901;
    private String ip;
    private int port;

    public InetAddress(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
