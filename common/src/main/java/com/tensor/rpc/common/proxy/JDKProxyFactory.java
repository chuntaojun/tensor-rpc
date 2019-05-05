package com.tensor.rpc.common.proxy;

/**
 * @author liaochuntao
 */
public class JDKProxyFactory extends ProxyFactory {

    @Override
    public <T> T create(Class<T> cls, RpcMethodInterceptor rpcMethodInterceptor) {
        return null;
    }
}
