package com.tensor.rpc.core.handler.filter;

import com.tensor.rpc.core.handler.FilterChain;
import com.tensor.rpc.core.handler.RpcExchange;
import com.tensor.rpc.core.handler.TensorFilter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class DefaultMethodFilter implements TensorFilter {

    private FilterChain chain;

    public DefaultMethodFilter() {
        ServiceLoader<TensorFilter> tmp = ServiceLoader.load(TensorFilter.class);
        LinkedList<TensorFilter> filters = new LinkedList<>();
        for (TensorFilter filter : tmp) {
            filters.add(filter);
        }
        filters.sort(Comparator.comparingInt(TensorFilter::priority));
        chain = initFilterChain(filters);
    }

    private FilterChain initFilterChain(LinkedList<TensorFilter> filters) {
        DefaultFilterChain chain = new DefaultFilterChain(null);
        DefaultFilterChain currentChain = chain;
        for (TensorFilter filter : filters) {
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
