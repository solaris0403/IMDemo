package com.caowei.im.sdk.message;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<IMSMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMSMsg message, ByteBuf byteBuf) throws Exception {
        //1.把实体序列化成字节数字
        byte[] bytes = JSON.toJSONBytes(message.getContent());
        //2.根据协议组装数据
        byteBuf.writeBytes(bytes);
    }
}
