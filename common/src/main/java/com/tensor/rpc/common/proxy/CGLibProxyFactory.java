package com.tensor.rpc.common.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author liaochuntao
 */
public class CGLibProxyFactory extends ProxyFactory {

    @Override
    public <T> T create(Class<T> cls, RpcMethodInterceptor interceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(interceptor);
        return (T) enhancer.create();
    }

}
