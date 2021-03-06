package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.client.filter.MethodExecutor;
import com.tensor.rpc.client.rpc.netty.NettyClient;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;

/**
 * @author liaochuntao
 */
public class RpcMethodExecutor implements MethodExecutor {

    @Override
    public RpcResult exec(RpcService rpcService, RpcMethodRequest request) throws InterruptedException {
        String serviceName = rpcService.serviceName();
        String ip = rpcService.ip();
        int port = rpcService.port();
        Channel channel = NettyClient.getConnection(serviceName, ip, port);
        channel.writeAndFlush(request);
        NettyClient.release(channel, serviceName, ip, port);
        if (Void.class.equals(request.getReturnType())) {
            return RpcResultPool.createFuture(request.getReqId(), true);
        }
        return RpcResultPool.createFuture(request.getReqId());
    }

    @Override
    public RpcResult exec(Channel channel, RpcMethodRequest request) throws InterruptedException {
        throw new RuntimeException("应该由本地方法执行");
    }

    @Override
    public MethodExecutor nextChain(MethodExecutor chain) {
        return chain;
    }


}
