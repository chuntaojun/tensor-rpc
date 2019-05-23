package com.tensor.rpc.client.cache;

import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.common.pojo.RpcMethodResponse;
import com.tensor.rpc.common.serialize.gson.GsonSerializer;
import com.tensor.rpc.common.util.KeyBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class NaticeMethodPool {

    private static final ConcurrentHashMap<String, NaticeMethodPool.RegisterInfo> METHOD_EXECUTOR = new ConcurrentHashMap<>();

    public static void register(Object owner, Class<?> cls) {
        Class[] interfaces = cls.getInterfaces();
        for (Class inter : interfaces) {
            METHOD_EXECUTOR.put(inter.getCanonicalName(), new RegisterInfo(owner, inter));
        }
    }

    public static Executor getExecutor(String key) {
        return new Executor(METHOD_EXECUTOR.get(key));
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

    public static class Executor implements Function<RpcMethodRequest, RpcMethodResponse> {

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
            return RpcMethodResponse
                    .builder()
                    .respId(request.getReqId())
                    .returnVal(GsonSerializer.encode(result))
                    .returnType(request.getReturnType())
                    .error(err)
                    .build();
        }
    }

    public static boolean isNative(RpcMethodRequest request) {
        String key = request.getOwnerName();
        return CachePool.containRegisterInfo(key);
    }

}
