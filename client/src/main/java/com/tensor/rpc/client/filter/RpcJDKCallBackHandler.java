package com.tensor.rpc.client.filter;

import com.tensor.rpc.common.call.AbstractRpcCallBackHandler;

import java.lang.reflect.Method;

/**
 * @author liaochuntao
 */
public class RpcJDKCallBackHandler extends AbstractRpcCallBackHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return super.invoke(proxy, method, args);
    }
}
