package com.tensor.rpc.common.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;

public interface RpcMethodInterceptor extends MethodInterceptor, InvocationHandler {
}
