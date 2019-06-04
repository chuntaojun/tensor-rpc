package com.tensor.rpc.core.handler;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public interface FilterChain {

    /**
     *
     * @param exchange
     * @return
     */
    void filter(RpcExchange exchange);

}
