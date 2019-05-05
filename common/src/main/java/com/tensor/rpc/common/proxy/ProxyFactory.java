package com.tensor.rpc.common.proxy;

/**
 * @author liaochuntao
 */
public abstract class ProxyFactory {

    /**
     *
     * @param cls
     * @param rpcMethodInterceptor
     * @param <T>
     * @return
     */
    abstract public <T> T create(Class<T> cls, RpcMethodInterceptor rpcMethodInterceptor);

}
