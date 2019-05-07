package com.tensor.rpc.server.handler;

import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.common.pojo.RpcMethodResponse;
import com.tensor.rpc.common.serialize.gson.GsonSerializer;
import com.tensor.rpc.common.serialize.kryo.KryoSerializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liaochuntao
 */
public class HeartChannelHandler extends SimpleChannelInboundHandler<RpcMethodRequest> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMethodRequest msg) throws Exception {
        System.out.println("msg is " + msg);
        RpcMethodResponse response = RpcMethodResponse
                .builder()
                .respId(msg.getReqId())
                .returnVal(GsonSerializer.encode("hello"))
                .returnType(String.class)
                .build();
        ctx.writeAndFlush(response).addListener(future -> System.out.println(future.isSuccess()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
