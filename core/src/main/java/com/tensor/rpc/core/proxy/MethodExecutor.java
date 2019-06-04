package com.tensor.rpc.core.proxy;

import com.tensor.rpc.core.handler.MethodExecutorChain;
import com.tensor.rpc.core.handler.Invoker;
import com.tensor.rpc.core.handler.RpcResult;

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
