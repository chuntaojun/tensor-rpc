package com.tensor.rpc.test;

import com.tensor.rpc.client.EnableRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liaochuntao
 */
@EnableRpc(server = "127.0.0.1:8333", ip = "127.0.0.1", port = 8250)
@SpringBootApplication
public class RpcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcTestApplication.class);
    }

}
