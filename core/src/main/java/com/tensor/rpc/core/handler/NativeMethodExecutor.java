package com.tensor.rpc.core.handler;

import com.tensor.rpc.core.config.ApplicationManager;
import com.tensor.rpc.core.proxy.MethodExecutor;
import com.tensor.rpc.core.schedule.RpcSchedule;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author liaochuntao
 */
public class NativeMethodExecutor implements MethodExecutor {

    @Override
    public RpcResult invoke(Invoker invoker, MethodExecutorChain chain) throws InterruptedException {
        if (invoker.isNative()) {
            return innerExec(invoker.getRequest());
        }
        return chain.chain(invoker);
    }

    private RpcResult innerExec(RpcMethodRequest msg) {
        RpcResult[] future = new RpcResult[1];
        Mono.just(msg)
                .map(request -> ApplicationManager.getNativeMethodManager().getExecutor(request.getOwnerName()))
                .map(executor -> {
                    future[0] = RpcResultPool.createFuture(msg.getReqId());
                    return executor;
                })
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(f -> f.apply(msg))
                .subscribe(rpcMethodResponse -> future[0].complete(rpcMethodResponse));
        Thread.currentThread().interrupt();
        return future[0];
    }

    @Override
    public int priority() {
        return 1;
    }

}
