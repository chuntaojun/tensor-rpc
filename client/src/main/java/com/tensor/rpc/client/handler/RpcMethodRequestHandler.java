package com.tensor.rpc.client.handler;

import com.tensor.rpc.common.pojo.RpcMethodResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaochuntao
 */
@Slf4j
public class RpcMethodRequestHandler extends SimpleChannelInboundHandler<RpcMethodResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMethodResponse msg) throws Exception {
        RpcResultPool.getFuture(msg.getRespId()).complete(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
