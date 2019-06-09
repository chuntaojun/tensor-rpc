package com.tensor.rpc.core.handler;

import com.tensor.rpc.core.config.ApplicationManager;
import com.tensor.rpc.core.proxy.MethodExecutor;
import com.tensor.rpc.core.schedule.RpcSchedule;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;
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
        if (invoker.isRpcRequest()) {
            return innerExec(invoker.getRequest(), invoker.getChannel());
        }
        return chain.chain(invoker);
    }

    private RpcResult innerExec(RpcMethodRequest msg) {
        RpcResult[] future = new RpcResult[1];
        future[0] = RpcResultPool.createFuture(msg.getReqId());
        Mono.just(msg)
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(request -> ApplicationManager.getNativeMethodManager().getExecutor(request.getOwnerName()))
                .map(f -> f.apply(msg))
                .subscribe(rpcMethodResponse -> future[0].complete(rpcMethodResponse));
        return future[0];
    }

    private RpcResult innerExec(RpcMethodRequest msg, Channel channel) {
        Mono.just(msg)
                .map(request -> ApplicationManager.getNativeMethodManager().getExecutor(request.getOwnerName()))
                .map(executor -> executor)
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(f -> f.apply(msg))
                .subscribe(channel::writeAndFlush);
        return null;
    }

    @Override
    public int priority() {
        return 1;
    }

}
