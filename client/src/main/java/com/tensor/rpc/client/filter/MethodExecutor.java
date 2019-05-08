package com.tensor.rpc.client.filter;

import com.tensor.rpc.client.filter.exec.RpcResult;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;

/**
 * @author liaochuntao
 */
public interface MethodExecutor {

    /**
     *
     * @param service
     * @param request
     * @return
     */
    RpcResult exec(RpcService service, RpcMethodRequest request) throws InterruptedException;

    /**
     *
     * @param chain
     */
    MethodExecutor initChain(MethodExecutor chain);

}
