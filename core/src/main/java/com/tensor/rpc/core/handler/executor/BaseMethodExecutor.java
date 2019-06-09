package com.tensor.rpc.core.handler.executor;

import com.tensor.rpc.core.handler.Invoker;
import com.tensor.rpc.core.handler.MethodExecutorChain;
import com.tensor.rpc.core.handler.RpcResult;
import com.tensor.rpc.core.handler.MethodExecutor;

/**
 * @author liaochuntao
 */
public class BaseMethodExecutor implements MethodExecutor {

    public BaseMethodExecutor() {
    }

    @Override
    public RpcResult invoke(Invoker invoker, MethodExecutorChain chain) throws InterruptedException {
        return chain.chain(invoker);
    }

    @Override
    public int priority() {
        return -3;
    }


}
