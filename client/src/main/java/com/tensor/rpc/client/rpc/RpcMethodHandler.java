package com.tensor.rpc.client.rpc;

import com.tensor.rpc.client.filter.RpcResultPool;
import com.tensor.rpc.common.pojo.RpcMethodResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liaochuntao
 */
public class RpcMethodHandler extends SimpleChannelInboundHandler<RpcMethodResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMethodResponse msg) throws Exception {
        System.out.println("msg is " + msg);
        RpcResultPool.getFuture(msg.getRespId()).complete(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
