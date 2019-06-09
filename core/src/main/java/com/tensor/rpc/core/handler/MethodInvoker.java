package com.tensor.rpc.core.handler;

import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.constants.ConstantsAttribute;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 用于CGLib代理调用RPC远程调用
 *
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class MethodInvoker {

    private FilterChain filterChain;
    private MethodExecutorChain executorChain;

    public MethodInvoker() {

        initMethodExecutorChain();
        initFilterChain();
    }

    public RpcResult invoke(RpcMethodRequest request, RpcService service) {
        RpcExchange exchange = new RpcExchange(service, request);
        try {
            filterChain.filter(exchange);
            return (RpcResult) exchange.getAttribute(ConstantsAttribute.RESULT_FUTURE_ATTRIBUTE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public RpcResult invoke(RpcMethodRequest request, RpcService service, Channel channel) {
        RpcExchange exchange = new RpcExchange(service, request);
        try {
            exchange.addAttribute(ConstantsAttribute.CHANNEL_ATTRIBUTE, channel);
            filterChain.filter(exchange);
            return (RpcResult) exchange.getAttribute(ConstantsAttribute.RESULT_FUTURE_ATTRIBUTE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void initMethodExecutorChain() {
        ServiceLoader<MethodExecutor> serviceLoaders = ServiceLoader.load(MethodExecutor.class);
        List<MethodExecutor> executors = new LinkedList<>();

        for (MethodExecutor executor : serviceLoaders) {
            executors.add(executor);
        }

        executors.sort(Comparator.comparingInt(MethodExecutor::priority));

        DefaultMethodExecutorChain defaultMethodExecutorChain = new DefaultMethodExecutorChain(null);
        DefaultMethodExecutorChain currentChain = defaultMethodExecutorChain;
        for (MethodExecutor executor : executors) {
            DefaultMethodExecutorChain d = new DefaultMethodExecutorChain(executor);
            currentChain.setNext(d);
            currentChain = d;
        }
        this.executorChain = defaultMethodExecutorChain.getNext();
    }

    private void initFilterChain() {
        ServiceLoader<Filter> tmp = ServiceLoader.load(Filter.class);
        LinkedList<Filter> filters = new LinkedList<>();
        for (Filter filter : tmp) {
            filters.add(filter);
        }
        filters.sort(Comparator.comparingInt(Filter::priority));
        DefaultFilterChain filterChain = new DefaultFilterChain(null);

        if (filters.isEmpty()) {
            filterChain.setExecutorChain(executorChain);
            this.filterChain =  filterChain;
            return;
        }

        DefaultFilterChain currentChain = filterChain;
        for (Filter filter : filters) {
            DefaultFilterChain chain = new DefaultFilterChain(filter);
            currentChain.setNext(chain);
            currentChain.setExecutorChain(executorChain);
            currentChain = chain;
        }

        this.filterChain = currentChain.getNext();
    }

}
