package com.tensor.rpc.test;

import com.tensor.rpc.common.annotation.RpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liaochuntao
 */
@RestController
public class RpcController {

    @Autowired
    private Test1 test1;

    @RpcService(ip = "127.0.0.1", port = 8250)
    private MyRpcRegister myRpcRegister;

    @GetMapping
    public String test() {
        return myRpcRegister.print("hello");
    }


}
