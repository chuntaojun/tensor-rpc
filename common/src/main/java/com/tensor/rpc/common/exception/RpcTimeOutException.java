package com.tensor.rpc.common.exception;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class RpcTimeOutException extends Exception {

    public RpcTimeOutException(String message) {
        super(message);
    }

    public RpcTimeOutException(Throwable cause) {
        super(cause);
    }
}
