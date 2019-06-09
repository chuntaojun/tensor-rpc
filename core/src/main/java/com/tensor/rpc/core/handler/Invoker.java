package com.tensor.rpc.core.handler;

import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.pojo.RpcMethodRequest;
import com.tensor.rpc.core.config.ApplicationManager;
import io.netty.channel.Channel;


/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class Invoker {

    private final RpcMethodRequest request;
    private final Channel channel;
    private RpcService service;
    private long startTime;
    private long endTime;

    public Invoker(RpcMethodRequest request, Channel channel) {
        this.request = request;
        this.channel = channel;
    }

    public Invoker(RpcService service, RpcMethodRequest request, Channel channel) {
        this.service = service;
        this.request = request;
        this.channel = channel;
    }

    public RpcMethodRequest getRequest() {
        return request;
    }

    Channel getChannel() {
        return channel;
    }

    RpcService getService() {
        return service;
    }

    void setService(RpcService service) {
        this.service = service;
    }

    void start() {
        this.startTime = System.currentTimeMillis();
    }

    void end() {
        this.endTime = System.currentTimeMillis();
    }

    boolean isNative() {
        return ApplicationManager.getNativeMethodManager().isNative(request) && channel == null;
    }

    boolean isRpcRequest() {
        return channel != null;
    }

}
