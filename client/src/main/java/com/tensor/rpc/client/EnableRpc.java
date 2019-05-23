package com.tensor.rpc.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@ComponentScan
@Inherited
public @interface EnableRpc {

    String server();
    String ip();
    int port();

}
