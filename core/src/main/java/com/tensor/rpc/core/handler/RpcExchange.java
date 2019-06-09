package com.tensor.rpc.core.handler;

import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class RpcExchange {

    private final RpcService rpcService;
    private final RpcMethodRequest request;
    private final Map<Object, Object> attachment = new HashMap<>();

    public RpcExchange(RpcService service, RpcMethodRequest request) {
        this.rpcService = service;
        this.request = request;
    }

    public RpcService getRpcService() {
        return rpcService;
    }

    public RpcMethodRequest getRequest() {
        return request;
    }

    public Object getAttribute(Object key) {
        return attachment.get(key);
    }

    public void addAttribute(Object key, Object val) {
        attachment.put(key, val);
    }

    public void removeAttribute(Object key) {
        attachment.remove(key);
    }

}
