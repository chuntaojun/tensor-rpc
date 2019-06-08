package com.tensor.rpc.core.handler.filter;

import com.tensor.rpc.core.handler.FilterChain;
import com.tensor.rpc.core.handler.Invoker;
import com.tensor.rpc.core.handler.RpcExchange;
import com.tensor.rpc.core.handler.Filter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class DefaultMethodFilter implements Filter {

    private FilterChain chain;

    public DefaultMethodFilter() {
        ServiceLoader<Filter> tmp = ServiceLoader.load(Filter.class);
        LinkedList<Filter> filters = new LinkedList<>();
        for (Filter filter : tmp) {
            filters.add(filter);
        }
        filters.sort(Comparator.comparingInt(Filter::priority));
        chain = initFilterChain(filters);
    }

    private FilterChain initFilterChain(LinkedList<Filter> filters) {
        DefaultFilterChain chain = new DefaultFilterChain(null);
        DefaultFilterChain currentChain = chain;
        for (Filter filter : filters) {
            DefaultFilterChain filterChain = new DefaultFilterChain(filter);
            currentChain.setNext(filterChain);
            currentChain = filterChain;
        }
        return currentChain.getNext();
    }

    public FilterChain getChain() {
        return chain;
    }

    @Override
    public void filter(RpcExchange exchange, FilterChain chain) {
        if (chain != null) {
            chain.filter(exchange);
        }
    }

    @Override
    public int priority() {
        return 0;
    }
}
