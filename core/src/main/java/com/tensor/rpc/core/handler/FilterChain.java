package com.tensor.rpc.core.handler;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public interface FilterChain {

    /**
     * RPC 进入 MethodExecutor 之前的 {@link FilterChain}
     *
     * @param exchange {@link RpcExchange}
     * @return {@link Void}
     */
    void filter(RpcExchange exchange) throws InterruptedException;

}
