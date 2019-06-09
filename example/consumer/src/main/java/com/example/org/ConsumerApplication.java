package com.example.org;

import com.tensor.rpc.common.constants.RpcType;
import com.tensor.rpc.core.EnableTensorRPC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@EnableTensorRPC(type = RpcType.CONSUMER)
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }

}
