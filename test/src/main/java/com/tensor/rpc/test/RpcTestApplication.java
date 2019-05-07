package com.tensor.rpc.test;

import com.tensor.rpc.client.config.RpcInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liaochuntao
 */
@RpcInit
@SpringBootApplication
public class RpcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcTestApplication.class);
    }

}
