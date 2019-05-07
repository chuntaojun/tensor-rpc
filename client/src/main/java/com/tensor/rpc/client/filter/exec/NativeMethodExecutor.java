package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.client.schedule.RpcSchedule;
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
public class NativeMethodExecutor {

    private static ConcurrentHashMap<String, RegisterInfo> methodExecutor = new ConcurrentHashMap<>();

    public static void register(Object owner, Class<?> cls) {
        Class[] interfaces = cls.getInterfaces();
        for (Class inter : interfaces) {
            methodExecutor.put(inter.getCanonicalName(), new RegisterInfo(owner, inter));
        }
    }

    public static void exec(RpcMethodRequest msg, Channel channel) {
        Mono.just(msg)
                .map(request -> methodExecutor.get(request.getOwnerName()))
                .publishOn(Schedulers.fromExecutor(RpcSchedule.RpcExecutor.RPC))
                .map(Executor::new)
                .map(f -> f.apply(msg))
                .subscribe(response -> channel.writeAndFlush(response)
                        .addListener(future -> System.out.println(future.isSuccess())));
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
}
