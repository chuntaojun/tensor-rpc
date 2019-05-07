package com.tensor.rpc.test;

import com.tensor.rpc.common.annotation.RpcRegister;
import org.springframework.stereotype.Service;

/**
 * @author liaochuntao
 */
@Service
@RpcRegister(ip = "127.0.0.1", port = 8888, serviceName = "MyRpcRegister")
public class MyRpcRegisterImpl implements MyRpcRegister {

    @Override
    public String print(String s) {
        return "MyRpcRegisterImpl";
    }
}
