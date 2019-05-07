package com.tensor.rpc.client.filter;

import com.tensor.rpc.client.cache.CachePool;
import com.tensor.rpc.client.config.netty.NettyClient;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.call.RpcCallBackHandler;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

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
        String serviceName = rpcService.serviceName();
        String ip = rpcService.ip();
        int port = rpcService.port();
        RpcMethodRequest request = methodRequest(cls.getCanonicalName(), method, args);
        Channel channel = NettyClient.getConnection(serviceName, ip, port);
        channel.writeAndFlush(request);
        NettyClient.release(channel, serviceName, ip, port);
        return null;
    }

    private RpcMethodRequest methodRequest(String className, Method method, Object[] args) {
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        return RpcMethodRequest.builder()
                .methodName(className + "." + methodName)
                .param(args)
                .returnType(returnType)
                .build();
    }
}
