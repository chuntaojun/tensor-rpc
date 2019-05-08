package com.tensor.rpc.client.filter;

import com.tensor.rpc.client.cache.CachePool;
import com.tensor.rpc.client.filter.exec.RpcResult;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.call.RpcCallBackHandler;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.common.util.KeyBuilder;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.UUID;

import static com.tensor.rpc.client.rpc.RpcConfigure.getMethodExecutor;

/**
 * @author liaochuntao
 */
public class RpcCGlibCallBackHandler extends RpcCallBackHandler {

    private String serviceName;

    public RpcCGlibCallBackHandler(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?> cls = method.getDeclaringClass();
        RpcService rpcService = CachePool.getRpcService(cls.getCanonicalName());
        RpcResult future = getMethodExecutor().exec(rpcService, methodRequest(cls.getCanonicalName(), method, args));
        return future.result();
    }

    private RpcMethodRequest methodRequest(String className, Method method, Object[] args) {
        String reqId = UUID.randomUUID().toString();
        Class<?> returnType = method.getReturnType();
        return RpcMethodRequest.builder()
                .reqId(reqId)
                .ownerName(className)
                .methodName(KeyBuilder.methodSign(method))
                .param(args)
                .returnType(returnType)
                .build();
    }

}
