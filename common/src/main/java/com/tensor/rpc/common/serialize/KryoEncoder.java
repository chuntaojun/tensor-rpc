package com.tensor.rpc.common.serialize;

import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liaochuntao
 */
public class KryoEncoder extends MessageToByteEncoder<RpcMethodRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcMethodRequest msg, ByteBuf out) throws Exception {
        KryoSerializer.serialize(msg, out);
        ctx.flush();
    }

}
