package com.tensor.rpc.client.config;

import com.tensor.rpc.client.Main;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({Main.class})
public @interface RpcInit {

    String serverAddr() default "127.0.0.1:8250";

}
