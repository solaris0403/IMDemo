package com.caowei.im.sdk.netty;

public class Packet {
    private int length;
    private Message message;

    public Packet(int length, Message message) {
        this.length = length;
        this.message = message;
    }
}
