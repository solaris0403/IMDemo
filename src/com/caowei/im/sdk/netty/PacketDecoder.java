package com.caowei.im.sdk.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

public class PacketDecoder extends LengthFieldBasedFrameDecoder {
    //byteOrder 表示字节流表示的数据是大端还是小端，用于长度域的读取；
    private static final ByteOrder BYTE_ORDER = null;
    //maxFrameLength 表示的是包的最大长度，超出包的最大长度netty将会做一些特殊处理；
    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;
    //lengthFieldOffset 指的是长度域的偏移量，表示跳过指定长度个字节之后的才是长度域；
    private static final int LENGTH_FIELD_OFFSET = 0;
    //lengthFieldLength 记录该帧数据长度的字段本身的长度；
    private static final int LENGTH_FIELD_LENGTH = 4;
    //lengthAdjustment 该字段加长度字段等于数据帧的长度，包体长度调整的大小，长度域的数值表示的长度加上这个修正值表示的就是带header的包；
    private static final int LENGTH_ADJUSTMENT = 0;
    //initialBytesToStrip 从数据帧中跳过的字节数，表示获取完一个完整的数据包之后，忽略前面的指定的位数个字节，应用解码器拿到的就是不带长度域的数据包；
    private static final int INITIAL_BYTES_TO_STRIP = 0;
    //failFast 如果为true，则表示读取到长度域，TA的值的超过maxFrameLength，就抛出一个 TooLongFrameException，
    //而为false表示只有当真正读取完长度域的值表示的字节之后，才会抛出 TooLongFrameException，默认情况下设置为true，建议不要修改，否则可能会造成内存溢出。
    private static final boolean FAIL_FAST = false;

    /**
     * Message类中定义了length，放在消息头部, length占4个字节所以头部总长度是4个字节
     */
    private static final int HEADER_SIZE = LENGTH_FIELD_LENGTH;

    public PacketDecoder() {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }
}
