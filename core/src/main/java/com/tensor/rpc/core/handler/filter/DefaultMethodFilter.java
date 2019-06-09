package com.tensor.rpc.core.handler.filter;

import com.tensor.rpc.core.handler.BaseMethodExecutor;
import com.tensor.rpc.core.handler.FilterChain;
import com.tensor.rpc.core.handler.Invoker;
import com.tensor.rpc.core.handler.RpcExchange;
import com.tensor.rpc.core.handler.Filter;
import com.tensor.rpc.core.proxy.MethodExecutor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ServiceLoader;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class DefaultMethodFilter implements Filter {


    public DefaultMethodFilter() {

    }

    @Override
    public void filter(RpcExchange exchange, FilterChain chain) throws InterruptedException {
        if (chain != null) {
            chain.filter(exchange);
        }
    }

    @Override
    public int priority() {
        return 0;
    }
}
