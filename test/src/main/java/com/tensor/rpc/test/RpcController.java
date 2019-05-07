package com.tensor.rpc.test;

import com.tensor.rpc.common.annotation.RpcService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/**
 * @author liaochuntao
 */
@RestController
public class RpcController {

    @RpcService(ip = "127.0.0.1", port = 8250)
    private MyRpcService myRpcServicel;

    @GetMapping
    public void test() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            list.add(i);
        }
        myRpcServicel.print("hello", list);
    }


}
