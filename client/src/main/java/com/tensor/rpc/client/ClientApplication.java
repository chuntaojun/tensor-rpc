package com.tensor.rpc.client;

import com.tensor.rpc.client.config.RpcConfigure;
import com.tensor.rpc.client.config.netty.NettyClientConfigure;
import io.netty.channel.Channel;


/**
 * @author liaochuntao
 */
public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        RpcConfigure rpcConfigure = new RpcConfigure();
        rpcConfigure.setExposeIp("127.0.0.1");
        rpcConfigure.setExposePort(8081);
        rpcConfigure.setServiceName("chuntao");
        RpcConfigure.init(rpcConfigure);
        Channel channel = new NettyClientConfigure().getConnection("tensor", "192.168.31.217", 8080);
        channel.closeFuture().sync();
    }

}
