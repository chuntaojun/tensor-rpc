package com.tensor.rpc.core.handler;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public interface MethodExecutorChain {

    RpcResult chain(Invoker invoker) throws InterruptedException;

}
