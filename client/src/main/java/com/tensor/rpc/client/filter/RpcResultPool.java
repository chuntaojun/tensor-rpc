package com.tensor.rpc.client.filter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaochuntao
 */
public class RpcResultPool {

    private static final ConcurrentHashMap<String, RpcResult> RESULT_FUTURE = new ConcurrentHashMap<>();

    public static RpcResult getFuture(String key) {
        return RESULT_FUTURE.get(key);
    }

    public static RpcResult createFuture(String key) {
        RpcResult result = new RpcResult();
        RESULT_FUTURE.put(key, result);
        return result;
    }

    public static void remove(String key) {
        RESULT_FUTURE.remove(key);
    }

}
