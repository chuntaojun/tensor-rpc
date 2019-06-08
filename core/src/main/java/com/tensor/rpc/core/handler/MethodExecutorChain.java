package com.tensor.rpc.core.handler;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public interface MethodExecutorChain {

    /**
     * RPC 任务执行链
     *
     * @param invoker {@link Invoker}
     * @return {@link RpcResult}
     * @throws InterruptedException
     */
    RpcResult chain(Invoker invoker) throws InterruptedException;

}
