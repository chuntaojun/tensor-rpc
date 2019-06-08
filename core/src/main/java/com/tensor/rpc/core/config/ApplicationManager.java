package com.tensor.rpc.core.config;

import com.tensor.rpc.common.annotation.RpcRegister;
import com.tensor.rpc.common.annotation.RpcService;
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
public class ApplicationManager {

    private static final RpcInfoManager RPC_INFO_MANAGER = new RpcInfoManager();
    private static final NativeMethodManager NATIVE_METHOD_MANAGER = new NativeMethodManager();

    public static RpcInfoManager getRpcInfoManager() {
        return RPC_INFO_MANAGER;
    }

    public static NativeMethodManager getNativeMethodManager() {
        return NATIVE_METHOD_MANAGER;
    }

    public static class RpcInfoManager {

        RpcInfoManager() {}

        private final ConcurrentHashMap<String, RpcService> SERVICE_MAP = new ConcurrentHashMap<>();
        private final ConcurrentHashMap<String, RpcRegister> REGISTER_MAP = new ConcurrentHashMap<>();

        public void consumerRegister(String key, RpcService value) {
            SERVICE_MAP.put(key, value);
        }

        public void providerRegister(String key, RpcRegister value) {
            REGISTER_MAP.put(key, value);
        }

        public RpcService getRpcService(String key) {
            return SERVICE_MAP.get(key);
        }

        public RpcRegister getRpcRegister(String key) {
            return REGISTER_MAP.get(key);
        }

        public boolean containRegisterInfo(String key) {
            return REGISTER_MAP.containsKey(key);
        }

    }

    public static class NativeMethodManager {
        private final ConcurrentHashMap<String, RegisterInfo> METHOD_EXECUTOR = new ConcurrentHashMap<>();

        NativeMethodManager() {}

        public void register(Object owner, Class<?> cls) {
            Class[] interfaces = cls.getInterfaces();
            for (Class inter : interfaces) {
                METHOD_EXECUTOR.put(inter.getCanonicalName(), new RegisterInfo(owner, inter));
            }
        }

        public Executor getExecutor(String key) {
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

        public boolean isNative(RpcMethodRequest request) {
            String key = request.getOwnerName();
            return RPC_INFO_MANAGER.containRegisterInfo(key);
        }
    }

}
