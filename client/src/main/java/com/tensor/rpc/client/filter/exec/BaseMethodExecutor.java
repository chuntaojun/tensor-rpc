package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.client.filter.MethodExecutor;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;

/**
 * @author liaochuntao
 */
public class BaseMethodExecutor implements MethodExecutor {

    private MethodExecutor chain;

    public BaseMethodExecutor() {
    }

    @Override
    public RpcResult exec(RpcService rpcService, RpcMethodRequest request) throws InterruptedException {
        return chain.exec(rpcService, request);
    }

    @Override
    public RpcResult exec(Channel channel, RpcMethodRequest request) throws InterruptedException {
        return chain.exec(channel, request);
    }

    @Override
    public MethodExecutor nextChain(MethodExecutor chain) {
        this.chain = chain;
        return chain;
    }

}
