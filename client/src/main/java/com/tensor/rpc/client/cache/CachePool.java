package com.tensor.rpc.client.cache;

import com.tensor.rpc.common.annotation.RpcRegister;
import com.tensor.rpc.common.annotation.RpcService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaochuntao
 */
public class CachePool {

    private static final ConcurrentHashMap<String, RpcService> serviceMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, RpcRegister> registerMap = new ConcurrentHashMap<>();

    public static void register(String key, RpcService value) {
        serviceMap.put(key, value);
    }

    public static void register(String key, RpcRegister value) {
        registerMap.put(key, value);
    }

    public static RpcService getRpcService(String key) {
        return serviceMap.get(key);
    }

    public static RpcRegister getRpcRegister(String key) {
        return registerMap.get(key);
    }

}
