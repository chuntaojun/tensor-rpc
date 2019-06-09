package com.example.org;

import com.tensor.rpc.common.annotation.RpcRegister;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Service
@RpcRegister(value = LCTInterface.class)
public class LCTInterfaceImpl implements LCTInterface {

    @Override
    public String print(String s) {
        return "RPC : [" + s + "]";
    }
}
