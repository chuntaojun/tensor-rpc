package com.tensor.rpc.common.proxy.filter;

import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.proxy.CGLibProxyFactory;
import com.tensor.rpc.common.proxy.JDKProxyFactory;
import com.tensor.rpc.common.proxy.ProxyFactory;

/**
 * @author liaochuntao
 */
public class RpcServiceProxy {

    public static final ProxyFactory CGLIB_PROXY = new CGLibProxyFactory();
    public static final ProxyFactory JDK_PROXY = new JDKProxyFactory();

    public static <T> T inject(RpcService rpcService, Class<T> cls) {
        return create(cls, CGLIB_PROXY, rpcService.serviceName());
    }

    private static <T> T create(Class<T> cls, ProxyFactory proxyFactory, String serviceName) {
        T target = proxyFactory.create(cls, new RpcCGlibCallBackHandler(serviceName));
        return target;
    }

}
