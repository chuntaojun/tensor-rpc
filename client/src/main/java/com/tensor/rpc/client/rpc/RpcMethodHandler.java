package com.tensor.rpc.client.rpc;

import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liaochuntao
 */
public class RpcMethodHandler extends SimpleChannelInboundHandler<RpcMethodRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMethodRequest msg) throws Exception {

    }
}
