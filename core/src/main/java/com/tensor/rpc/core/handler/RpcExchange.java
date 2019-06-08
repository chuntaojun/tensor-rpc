package com.tensor.rpc.core.handler;

import com.tensor.rpc.common.pojo.RpcMethodRequest;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class RpcExchange {

    private final RpcMethodRequest request;
    private final Channel channel;
    private final Map<Object, Object> attachment = new HashMap<>();

    public RpcExchange(RpcMethodRequest request, Channel channel) {
        this.request = request;
        this.channel = channel;
    }

    public RpcMethodRequest getRequest() {
        return request;
    }

    public Channel getChannel() {
        return channel;
    }

    public void addAttribute(Object key, Object val) {
        attachment.put(key, val);
    }

    public void removeAttribute(Object key) {
        attachment.remove(key);
    }

}
