package com.tensor.rpc.client;

import com.tensor.rpc.client.config.NettyClientConfigure;

/**
 * @author liaochuntao
 */
public class ClientApplication {

    public static void main(String[] args) {
        new NettyClientConfigure().connect(8080, "192.168.31.217");
    }

}
