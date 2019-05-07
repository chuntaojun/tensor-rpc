package com.tensor.rpc.client.filter;

import com.tensor.rpc.common.call.RpcCallBackHandler;

import java.lang.reflect.Method;

/**
 * @author liaochuntao
 */
public class RpcJDKCallBackHandler extends RpcCallBackHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.invoke(proxy, method, args);
    }
}
