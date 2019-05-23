package com.tensor.rpc.client.handler;

import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.tensor.rpc.client.rpc.RpcConfigure.getMethodExecutor;

/**
 * @author liaochuntao
 */
public class RpcMethodResponsetHandler extends SimpleChannelInboundHandler<RpcMethodRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMethodRequest msg) throws Exception {
        getMethodExecutor().exec(ctx.channel(), msg);
    }
}
