package com.tensor.rpc.core.handler;

import com.tensor.rpc.core.proxy.MethodExecutor;
import com.tensor.rpc.core.handler.filter.DefaultMethodFilter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author liaochuntao
 */
public class BaseMethodExecutor implements MethodExecutor {

    private MethodExecutorChain chain;
    private DefaultMethodFilter filter = new DefaultMethodFilter();

    public BaseMethodExecutor() {
        init();
    }

    @Override
    public RpcResult invoke(Invoker invoker, MethodExecutorChain chain) throws InterruptedException {
        invoker.start();
        RpcExchange exchange = new RpcExchange(invoker.getRequest(), invoker.getChannel());
        filter.filter(exchange, filter.getChain());
        invoker.end();
        return this.chain.chain(invoker);
    }

    @Override
    public int priority() {
        return 0;
    }

    private void init() {

        ServiceLoader<MethodExecutor> serviceLoaders = ServiceLoader.load(MethodExecutor.class);
        List<MethodExecutor> executors = new LinkedList<>();

        for (MethodExecutor executor : serviceLoaders) {
            executors.add(executor);
        }

        NativeMethodExecutor nativeMethodExecutor = new NativeMethodExecutor();
        RpcMethodExecutor rpcMethodExecutor = new RpcMethodExecutor();

        executors.sort(Comparator.comparingInt(MethodExecutor::priority));
        executors.add(nativeMethodExecutor);
        executors.add(rpcMethodExecutor);

        DefaultMethodExecutorChain defaultMethodExecutorChain = new DefaultMethodExecutorChain(null);
        DefaultMethodExecutorChain currentChain = defaultMethodExecutorChain;
        for (MethodExecutor executor : executors) {
            DefaultMethodExecutorChain d = new DefaultMethodExecutorChain(executor);
            currentChain.setNext(d);
            currentChain = d;
        }
        this.chain = defaultMethodExecutorChain.getNext();
    }

}
