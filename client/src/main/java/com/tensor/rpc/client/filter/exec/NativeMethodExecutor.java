package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.client.cache.CachePool;
import com.tensor.rpc.client.filter.MethodExecutor;
import com.tensor.rpc.client.schedule.RpcSchedule;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.common.pojo.RpcMethodResponse;
import com.tensor.rpc.common.serialize.gson.GsonSerializer;
import com.tensor.rpc.common.util.KeyBuilder;
import io.netty.channel.Channel;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author liaochuntao
 */
public class NativeMethodExecutor implements MethodExecutor {

    private static ConcurrentHashMap<String, RegisterInfo> methodExecutor = new ConcurrentHashMap<>();

    private MethodExecutor chain;

    public NativeMethodExecutor() {
    }

    @Override
    public MethodExecutor initChain(MethodExecutor chain) {
        this.chain = chain;
        return chain;
    }

    @Override
    public RpcResult exec(RpcService service, RpcMethodRequest request) throws InterruptedException {
        if (isNative(request)) {
            return exec(request);
        }
        return chain.exec(service, request);
    }

    protected RpcResult exec(RpcMethodRequest msg) {
        RpcResult[] future = new RpcResult[1];
        Mono.just(msg)
                .map(request -> methodExecutor.get(request.getOwnerName()))
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(Executor::new)
                .map(f -> f.apply(msg))
                .subscribe(rpcMethodResponse -> {
                    future[0] = RpcResultPool.getFuture(rpcMethodResponse.getRespId());
                    future[0].complete(rpcMethodResponse);
                });
        return future[0];
    }

    public void exec(RpcMethodRequest msg, Channel channel) {
        Mono.just(msg)
                .map(request -> methodExecutor.get(request.getOwnerName()))
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(Executor::new)
                .map(f -> f.apply(msg))
                .subscribe(response -> channel.writeAndFlush(response)
                        .addListener(future -> System.out.println(future.isSuccess())));
    }

    public static void register(Object owner, Class<?> cls) {
        Class[] interfaces = cls.getInterfaces();
        for (Class inter : interfaces) {
            methodExecutor.put(inter.getCanonicalName(), new RegisterInfo(owner, inter));
        }
    }

    private static class RegisterInfo {

        Object owner;
        Map<String, Method> methodMap = new HashMap<>();

        RegisterInfo(Object owner, Class<?> cls) {

            this.owner = owner;

            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                methodMap.put(KeyBuilder.methodSign(method), method);
            }
        }

        Method getMethod(String sign) {
            return methodMap.get(sign);
        }

    }

    private static class Executor implements Function<RpcMethodRequest, RpcMethodResponse> {

        RegisterInfo worker;

        Executor(RegisterInfo worker) {
            this.worker = worker;
        }

        @Override
        public RpcMethodResponse apply(RpcMethodRequest request) {
            Exception err = null;
            Object result = new Object();
            if (worker == null) {
                err = new Exception("[Tensor RPC] : Not this function");
            } else {
                Method method = worker.getMethod(request.getMethodName());
                try {
                    result = method.invoke(worker.owner, request.getParam());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    err = e;
                }
            }
            RpcMethodResponse response = RpcMethodResponse
                    .builder()
                    .respId(request.getReqId())
                    .returnVal(GsonSerializer.encode(result))
                    .returnType(request.getReturnType())
                    .error(err)
                    .build();
            return response;
        }
    }

    protected static boolean isNative(RpcMethodRequest request) {
        String key = request.getOwnerName();
        return CachePool.containRegisterInfo(key);
    }
}
