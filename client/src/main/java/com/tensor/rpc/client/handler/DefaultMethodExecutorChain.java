package com.tensor.rpc.client.handler;

import com.tensor.rpc.client.core.MethodExecutor;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class DefaultMethodExecutorChain implements MethodExecutorChain {

    private MethodExecutor currentExecutor;
    private MethodExecutorChain next;

    public DefaultMethodExecutorChain(MethodExecutor currentExecutor) {
        this.currentExecutor = currentExecutor;
    }

    public MethodExecutorChain getNext() {
        return next;
    }

    public void setNext(MethodExecutorChain next) {
        this.next = next;
    }

    @Override
    public RpcResult chain(Invoker invoker) throws InterruptedException {
        if (next != null) {
            return currentExecutor.invoke(invoker, next);
        }
        return currentExecutor.invoke(invoker, null);
    }
}
