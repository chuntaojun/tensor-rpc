package com.tensor.rpc.client.filter.exec;

import com.tensor.rpc.common.exception.RpcTimeOutException;
import com.tensor.rpc.common.pojo.RpcMethodResponse;
import com.tensor.rpc.common.serialize.gson.GsonSerializer;

import java.util.concurrent.*;

/**
 * @author liaochuntao
 */
public class RpcResult extends CompletableFuture<RpcMethodResponse> {

    private boolean isVoid = false;

    public RpcResult() {
    }

    public RpcResult(boolean isVoid) {
        this.isVoid = isVoid;
    }

    @Override
    public boolean complete(RpcMethodResponse value) {
        return super.complete(value);
    }

    public Object result() throws ExecutionException, InterruptedException, RpcTimeOutException {
        if (isVoid) {
            return null;
        }
        RpcMethodResponse response = null;
        try {
            response = get(1000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            throw new RpcTimeOutException(e);
        }
        RpcResultPool.remove(response.getRespId());
        Class cls = response.getReturnType();
        String val = response.getReturnVal();
        Throwable error = response.getError();
        if (error != null) {
            throw new RuntimeException(error);
        }
        return GsonSerializer.decode(val, cls);
    }
}
