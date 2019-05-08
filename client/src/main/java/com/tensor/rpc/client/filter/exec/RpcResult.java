package com.tensor.rpc.client.filter.exec;

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

    public Object result() throws ExecutionException, InterruptedException {
        if (isVoid) {
            return null;
        }
        RpcMethodResponse response = get();
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
