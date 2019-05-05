package com.tensor.rpc.server;

import com.tensor.rpc.server.config.NettyServerConfigure;

/**
 * @author liaochuntao
 */
public class ServerApplication {

    public static void main(String[] args) {
        NettyServerConfigure.bind(8080);
    }

}
