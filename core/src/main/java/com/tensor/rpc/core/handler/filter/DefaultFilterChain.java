package com.tensor.rpc.core.handler.filter;

import com.tensor.rpc.core.handler.FilterChain;
import com.tensor.rpc.core.handler.MethodExecutorChain;
import com.tensor.rpc.core.handler.RpcExchange;
import com.tensor.rpc.core.handler.Filter;
import com.tensor.rpc.core.handler.RpcResult;

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

    @Override
    public void filter(RpcExchange exchange) {
        if (next != null) {
            currentFilter.filter(exchange, next);
        }
    }
}
