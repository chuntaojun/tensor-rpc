package com.tensor.rpc.client.core;

import com.tensor.rpc.client.handler.MethodExecutorChain;
import com.tensor.rpc.client.handler.Invoker;
import com.tensor.rpc.client.handler.RpcResult;

/**
 * @author liaochuntao
 */
public interface MethodExecutor {

    /**
     *
     * @param invoker
     * @return
     */
    RpcResult invoke(Invoker invoker, MethodExecutorChain chain) throws InterruptedException;

    /**
     * only can be {@link Integer}
     *
     * @return
     */
    int priority();

}
