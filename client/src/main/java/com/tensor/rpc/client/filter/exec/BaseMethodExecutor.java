package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.client.filter.MethodExecutor;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;

/**
 * @author liaochuntao
 */
public class BaseMethodExecutor implements MethodExecutor {

    private MethodExecutor chain;

    public BaseMethodExecutor() {
    }


    /**
     * 如果是 RPC 调用的是本地方法，则直接查找本地方法进行调用即可
     *
     * @param rpcService
     * @param request
     * @return
     * @throws InterruptedException
     */
    @Override
    public RpcResult exec(RpcService rpcService, RpcMethodRequest request) throws InterruptedException {
        return chain.exec(rpcService, request);
    }

    @Override
    public MethodExecutor initChain(MethodExecutor chain) {
        this.chain = chain;
        return chain;
    }

}
