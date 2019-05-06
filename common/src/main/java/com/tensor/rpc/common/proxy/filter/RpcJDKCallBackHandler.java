package com.tensor.rpc.common.proxy.filter;

import com.tensor.rpc.common.call.RpcCallBackHandler;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liaochuntao
 */
public class RpcJDKCallBackHandler extends RpcCallBackHandler {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return super.intercept(obj, method, args, proxy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.invoke(proxy, method, args);
    }
}
