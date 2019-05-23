package com.tensor.rpc.client.filter;

import com.tensor.rpc.client.filter.exec.RpcResult;
import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;

/**
 * @author liaochuntao
 */
public interface MethodExecutor {

    /**
     * 如果是 RPC 调用的是本地方法，则直接查找本地方法进行调用即可
     *
     * @param service {@link RpcService}
     * @param request {@link RpcMethodRequest}
     * @return {@link RpcResult}
     * @throws InterruptedException
     */
    RpcResult exec(RpcService service, RpcMethodRequest request) throws InterruptedException;

    /**
     *
     * @param channel {@link Channel}
     * @param request {@link RpcMethodRequest}
     * @return {@link RpcResult}
     * @throws InterruptedException
     */
    RpcResult exec(Channel channel, RpcMethodRequest request) throws InterruptedException;

    /**
     * 设置责任链
     *
     * @param chain {@link MethodExecutor}
     * @return {@link MethodExecutor}
     */
    MethodExecutor nextChain(MethodExecutor chain);

}
