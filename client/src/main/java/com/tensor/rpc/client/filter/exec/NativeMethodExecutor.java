package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.client.cache.NaticeMethodPool;
import com.tensor.rpc.client.filter.MethodExecutor;
import com.tensor.rpc.client.schedule.RpcSchedule;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author liaochuntao
 */
public class NativeMethodExecutor implements MethodExecutor {

    private MethodExecutor chain;

    public NativeMethodExecutor() {
    }

    @Override
    public MethodExecutor nextChain(MethodExecutor chain) {
        this.chain = chain;
        return chain;
    }

    @Override
    public RpcResult exec(RpcService service, RpcMethodRequest request) throws InterruptedException {
        if (NaticeMethodPool.isNative(request)) {
            return innerExec(request);
        }
        return chain.exec(service, request);
    }

    @Override
    public RpcResult exec(Channel channel, RpcMethodRequest request) throws InterruptedException {
        innerExec(request, channel);
        return null;
    }

    private RpcResult innerExec(RpcMethodRequest msg) {
        RpcResult[] future = new RpcResult[1];
        Mono.just(msg)
                .map(request -> NaticeMethodPool.getExecutor(request.getOwnerName()))
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(f -> f.apply(msg))
                .subscribe(rpcMethodResponse -> {
                    future[0] = RpcResultPool.getFuture(rpcMethodResponse.getRespId());
                    future[0].complete(rpcMethodResponse);
                });
        return future[0];
    }

    private void innerExec(RpcMethodRequest msg, Channel channel) {
        Mono.just(msg)
                .map(request -> NaticeMethodPool.getExecutor(request.getOwnerName()))
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(f -> f.apply(msg))
                .subscribe(response -> channel.writeAndFlush(response)
                        .addListener(future -> System.out.println(future.isSuccess())));
    }

}
