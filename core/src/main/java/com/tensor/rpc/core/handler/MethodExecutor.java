package com.tensor.rpc.core.handler;

/**
 * @author liaochuntao
 */
public interface MethodExecutor {

    /**
     * RPC 方法调用
     *
     * @param invoker {@link Invoker}
     * @param chain {@link MethodExecutorChain}
     * @return {@link RpcResult}
     * @throws InterruptedException
     */
    RpcResult invoke(Invoker invoker, MethodExecutorChain chain) throws InterruptedException;

    /**
     * only can be {@link Integer}
     *
     * @return
     */
    int priority();

}
