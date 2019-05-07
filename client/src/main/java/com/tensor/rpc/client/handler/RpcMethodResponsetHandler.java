package com.tensor.rpc.client.handler;

import com.tensor.rpc.client.filter.exec.NativeMethodExecutor;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author liaochuntao
 */
public class RpcMethodResponsetHandler extends SimpleChannelInboundHandler<RpcMethodRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMethodRequest msg) throws Exception {
        NativeMethodExecutor.exec(msg, ctx.channel());
    }
}
