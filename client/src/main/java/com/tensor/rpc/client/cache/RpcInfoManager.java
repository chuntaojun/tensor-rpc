package com.tensor.rpc.client.cache;

import com.tensor.rpc.common.annotation.RpcRegister;
import com.tensor.rpc.common.annotation.RpcService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaochuntao
 */
public class RpcInfoManager {

    private static final ConcurrentHashMap<String, RpcService> SERVICE_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, RpcRegister> REGISTER_MAP = new ConcurrentHashMap<>();

    public static void consumerRegister(String key, RpcService value) {
        SERVICE_MAP.put(key, value);
    }

    public static void providerRegister(String key, RpcRegister value) {
        REGISTER_MAP.put(key, value);
    }

    public static RpcService getRpcService(String key) {
        return SERVICE_MAP.get(key);
    }

    public static RpcRegister getRpcRegister(String key) {
        return REGISTER_MAP.get(key);
    }

    public static boolean containRegisterInfo(String key) {
        return REGISTER_MAP.containsKey(key);
    }

}
