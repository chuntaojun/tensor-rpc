package com.tensor.rpc.core.handler;

/**
 * 请求信息先经过拦截器进行处理
 *
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public interface Filter {

    /**
     *
     * @param exchange
     * @param chain
     * @return
     */
    void filter(RpcExchange exchange, FilterChain chain) throws InterruptedException;

    /**
     * only can be {@link Integer}
     *
     * @return
     */
    int priority();

}
