package com.caowei.im.sdk.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] respByte = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(respByte);
        IMSMsg.Builder builder = new IMSMsg.Builder();
        builder.setContent(new String(respByte, CharsetUtil.UTF_8));
        builder.build();
        IMSMsg message = new IMSMsg(builder);
        list.add(message);
    }
}
