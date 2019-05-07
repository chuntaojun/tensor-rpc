package com.tensor.rpc.common.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;


/**
 * @author liaochuntao
 */
public class KryoDecoder extends LengthFieldBasedFrameDecoder {

    public KryoDecoder(int maxFrameLength) {
        super(maxFrameLength,0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        int byteLength = decode.readableBytes();
        byte[] byteHolder = new byte[byteLength];
        decode.readBytes(byteHolder);
        return KryoSerializer.deserialize(byteHolder);
    }

}
