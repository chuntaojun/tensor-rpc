package com.tensor.rpc.common.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;

/**
 * @author liaochuntao
 */
public interface RpcMethodInterceptor extends MethodInterceptor, InvocationHandler {
}
