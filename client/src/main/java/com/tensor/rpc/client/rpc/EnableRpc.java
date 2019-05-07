package com.tensor.rpc.client.rpc;

import com.tensor.rpc.client.Main;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({Main.class})
@Inherited
public @interface EnableRpc {

    String server();
    String ip();
    int port();

}
