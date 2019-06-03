package com.tensor.rpc.client.handler;

import com.tensor.rpc.client.core.MethodExecutor;
import com.tensor.rpc.client.config.netty.NettyClient;
import com.tensor.rpc.client.schedule.RpcSchedule;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author liaochuntao
 */
@Slf4j
public class RpcMethodExecutor implements MethodExecutor {

    @Override
    public RpcResult invoke(Invoker invoker, MethodExecutorChain chain) throws InterruptedException {
        RpcResult[] future = new RpcResult[1];
        Mono.just(invoker)
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(invoker1 -> {
                    sendRequest(invoker1);
                    return invoker1.getRequest();
                })
                .onErrorResume(Exception.class, null)
                .subscribe(request -> {
                    if (request == null) {
                        throw new RuntimeException("[RPC 调用过程出现异常！]");
                    } else if (Void.class.equals(request.getReturnType())) {
                        future[0] = RpcResultPool.createFuture(request.getReqId(), true);
                    } else {
                        future[0] = RpcResultPool.createFuture(request.getReqId());
                    }
                });
        return future[0];
    }

    @Override
    public int priority() {
        return 2;
    }

    /**
     * 采用{@link com.tensor.rpc.client.discovery.LoadBlancer} 进行client端的负载均衡
     *
     * @param invoker
     */
    private void sendRequest(Invoker invoker) {
        RpcService rpcService = invoker.getService();
        RpcMethodRequest request = invoker.getRequest();
        String serviceName = rpcService.serviceName();
        String ip = rpcService.ip();
        int port = rpcService.port();
        Channel channel = null;
        try {
            channel = NettyClient.getConnection(serviceName, ip, port);
            channel.writeAndFlush(request).sync();
            NettyClient.release(channel, serviceName, ip, port);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
