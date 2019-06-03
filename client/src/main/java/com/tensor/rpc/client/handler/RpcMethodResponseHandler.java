package com.tensor.rpc.client.handler;

import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.tensor.rpc.client.config.RpcConfigure.getMethodExecutor;

/**
 * @author liaochuntao
 */
public class RpcMethodResponseHandler extends SimpleChannelInboundHandler<RpcMethodRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMethodRequest request) throws Exception {
        Invoker invoker = new Invoker(request, ctx.channel());
        getMethodExecutor().invoke(invoker, null);
    }
}
