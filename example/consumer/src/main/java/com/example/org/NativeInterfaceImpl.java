package com.example.org;

import com.tensor.rpc.common.annotation.RpcRegister;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Service
@RpcRegister(value = NativeInterface.class)
public class NativeInterfaceImpl implements NativeInterface {
    @Override
    public String print(String s) {
        return "Native : [" + s + "]";
    }
}
