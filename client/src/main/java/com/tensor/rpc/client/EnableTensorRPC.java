package com.tensor.rpc.client;

import com.tensor.rpc.client.bean.factory.TensorBeanRegister;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import(value = {TensorBeanRegister.class})
@Inherited
public @interface EnableTensorRPC {

    String server();
    String ip();
    int port();

}
