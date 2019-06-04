package com.tensor.rpc.core.handler;

import com.tensor.rpc.common.pojo.RpcMethodRequest;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class RpcExchange {

    private final RpcMethodRequest request;

    public RpcExchange(RpcMethodRequest request) {
        this.request = request;
    }

    public RpcMethodRequest getRequest() {
        return request;
    }
}
