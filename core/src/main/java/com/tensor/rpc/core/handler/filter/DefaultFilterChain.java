package com.tensor.rpc.core.handler.filter;

import com.tensor.rpc.core.handler.FilterChain;
import com.tensor.rpc.core.handler.RpcExchange;
import com.tensor.rpc.core.handler.TensorFilter;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class DefaultFilterChain implements FilterChain {

    private TensorFilter currentFilter;
    private FilterChain next;

    public DefaultFilterChain(TensorFilter currentFilter) {
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
