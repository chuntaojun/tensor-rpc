package com.example.org;

import com.tensor.rpc.common.annotation.RpcService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@RestController
public class MyController {

    @RpcService(ip = "127.0.0.1", port = 8250)
    private LCTInterface lctInterface;

    @RpcService(ip = "127.0.0.1", port = 8250)
    private NativeInterface nativeInterface;

    @GetMapping(value = "/v1/{name}")
    public String print1(@PathVariable(name = "name") String name) {
        return lctInterface.print(name);
    }

    @GetMapping(value = "/v2/{name}")
    public String print2(@PathVariable(name = "name") String name) {
        return nativeInterface.print(name);
    }

}
