package com.tensor.rpc.core.handler;

import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.constants.ConstantsAttribute;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.core.handler.filter.DefaultFilterChain;
import io.netty.channel.Channel;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * 用于CGLib代理调用RPC远程调用
 *
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class MethodInvoker {

    private FilterChain chain;

    public MethodInvoker() {
        ServiceLoader<Filter> tmp = ServiceLoader.load(Filter.class);
        LinkedList<Filter> filters = new LinkedList<>();
        for (Filter filter : tmp) {
            filters.add(filter);
        }
        filters.sort(Comparator.comparingInt(Filter::priority));
        this.chain = initFilterChain(filters, new BaseMethodExecutor());
    }

    public RpcResult invoke(RpcMethodRequest request, RpcService service) {
        RpcExchange exchange = new RpcExchange(service, request);
        try {
            chain.filter(exchange);
            return (RpcResult) exchange.getAttribute(ConstantsAttribute.RESULT_FUTURE_ATTRIBUTE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public RpcResult invoke(RpcMethodRequest request, RpcService service, Channel channel) {
        RpcExchange exchange = new RpcExchange(service, request);
        try {
            exchange.addAttribute(ConstantsAttribute.CHANNEL_ATTRIBUTE, channel);
            chain.filter(exchange);
            return (RpcResult) exchange.getAttribute(ConstantsAttribute.RESULT_FUTURE_ATTRIBUTE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private FilterChain initFilterChain(LinkedList<Filter> filters, BaseMethodExecutor executor) {
        DefaultFilterChain chain = new DefaultFilterChain(null);

        if (filters.isEmpty()) {
            chain.setExecutorChain(executor.getChain());
            return chain;
        }

        DefaultFilterChain currentChain = chain;
        for (Filter filter : filters) {
            DefaultFilterChain filterChain = new DefaultFilterChain(filter);
            currentChain.setNext(filterChain);
            currentChain.setExecutorChain(executor.getChain());
            currentChain = filterChain;
        }

        return currentChain.getNext();
    }

}
