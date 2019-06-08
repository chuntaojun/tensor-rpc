package com.tensor.rpc.core.proxy;

import com.tensor.rpc.core.config.ApplicationManager;
import com.tensor.rpc.core.handler.Invoker;
import com.tensor.rpc.core.handler.RpcResult;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.call.AbstractRpcCallBackHandler;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.common.util.KeyBuilder;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.UUID;

import static com.tensor.rpc.core.config.RpcApplication.getMethodExecutor;

/**
 * @author liaochuntao
 */
public class RpcCGlibCallBackHandler extends AbstractRpcCallBackHandler {

    private String serviceName;

    public RpcCGlibCallBackHandler(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?> cls = method.getDeclaringClass();
        RpcService rpcService = ApplicationManager.getRpcInfoManager().getRpcService(cls.getCanonicalName());
        RpcResult future = getMethodExecutor().invoke(buildInvoker(rpcService, cls.getCanonicalName(), method, args), null);
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

    private Invoker buildInvoker(RpcService rpcService, String className, Method method, Object[] args) {
        return new Invoker(rpcService, methodRequest(className, method, args), null);
    }

}
