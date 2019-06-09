package com.tensor.rpc.core.handler.executor;

import com.tensor.rpc.core.config.ApplicationManager;
import com.tensor.rpc.core.handler.Invoker;
import com.tensor.rpc.core.handler.MethodExecutorChain;
import com.tensor.rpc.core.handler.RpcResult;
import com.tensor.rpc.core.handler.RpcResultPool;
import com.tensor.rpc.core.handler.MethodExecutor;
import com.tensor.rpc.core.schedule.RpcSchedule;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author liaochuntao
 */
public class NativeMethodExecutor implements MethodExecutor {

    public NativeMethodExecutor() {
    }

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

    /**
     * 本地方法直接调用
     *
     * @param msg {@link RpcMethodRequest}
     * @return {@link RpcResult}
     */
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

    /**
     * 用于接收RPC Request请求，执行相应的方法
     *
     * @param msg {@link RpcMethodRequest}
     * @param channel {@link Channel}
     * @return {@link RpcResult}
     */
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
        return -2;
    }

}
