package com.tensor.rpc.client.handler;

import com.tensor.rpc.client.cache.NativeMethodManager;
import com.tensor.rpc.client.core.MethodExecutor;
import com.tensor.rpc.client.schedule.RpcSchedule;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author liaochuntao
 */
public class NativeMethodExecutor implements MethodExecutor {

    private MethodExecutor chain;

    public NativeMethodExecutor() {
    }

    private RpcResult innerExec(RpcMethodRequest msg) {
        RpcResult[] future = new RpcResult[1];
        Mono.just(msg)
                .map(request -> NativeMethodManager.getExecutor(request.getOwnerName()))
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(f -> f.apply(msg))
                .subscribe(rpcMethodResponse -> {
                    future[0] = RpcResultPool.getFuture(rpcMethodResponse.getRespId());
                    future[0].complete(rpcMethodResponse);
                });
        return future[0];
    }

    @Override
    public RpcResult invoke(Invoker invoker, MethodExecutorChain chain) throws InterruptedException {
        if (invoker.isNative()) {
            return innerExec(invoker.getRequest());
        }
        return chain.chain(invoker);
    }

    @Override
    public int priority() {
        return 1;
    }
}
