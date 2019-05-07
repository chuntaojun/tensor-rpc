package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.client.rpc.netty.NettyClient;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;

/**
 * @author liaochuntao
 */
public class RpcMethodExecutor {

    public static RpcResult exec(RpcService rpcService, RpcMethodRequest request) throws InterruptedException {
        String serviceName = rpcService.serviceName();
        String ip = rpcService.ip();
        int port = rpcService.port();
        Channel channel = NettyClient.getConnection(serviceName, ip, port);
        channel.writeAndFlush(request);
        NettyClient.release(channel, serviceName, ip, port);
        return RpcResultPool.createFuture(request.getReqId());
    }

    private class RpcReqTask implements Runnable {

        @Override
        public void run() {

        }
    }

}
