package com.tensor.rpc.common.proxy.filter;

import com.tensor.rpc.common.call.RpcCallBackHandler;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liaochuntao
 */
public class RpcCGlibCallBackHandler extends RpcCallBackHandler {

    private String serviceName;

    RpcCGlibCallBackHandler(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return super.intercept(obj, method, args, proxy);
    }
}
