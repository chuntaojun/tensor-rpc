package com.tensor.rpc.core.handler;

import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.constants.ConstantsAttribute;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.core.handler.FilterChain;
import com.tensor.rpc.core.handler.Invoker;
import com.tensor.rpc.core.handler.MethodExecutorChain;
import com.tensor.rpc.core.handler.RpcExchange;
import com.tensor.rpc.core.handler.Filter;
import com.tensor.rpc.core.handler.RpcResult;
import io.netty.channel.Channel;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class DefaultFilterChain implements FilterChain {

    private Filter currentFilter;
    private FilterChain next;
    private MethodExecutorChain executorChain;

    public DefaultFilterChain(Filter currentFilter) {
        this.currentFilter = currentFilter;
    }

    public FilterChain getNext() {
        return next;
    }

    public void setNext(FilterChain next) {
        this.next = next;
    }

    public void setExecutorChain(MethodExecutorChain executorChain) {
        this.executorChain = executorChain;
    }

    @Override
    public void filter(RpcExchange exchange) throws InterruptedException {
        if (next != null) {
            currentFilter.filter(exchange, next);
        } else {
            Invoker invoker = buildInvoker(exchange.getRpcService(), exchange.getRequest(),
                    (Channel) exchange.getAttribute(ConstantsAttribute.CHANNEL_ATTRIBUTE));
            RpcResult result = executorChain.chain(invoker);
            exchange.addAttribute(ConstantsAttribute.RESULT_FUTURE_ATTRIBUTE, result);
        }
    }

    private Invoker buildInvoker(RpcService rpcService, RpcMethodRequest request, Channel channel) {
        return new Invoker(rpcService, request, channel);
    }
}
