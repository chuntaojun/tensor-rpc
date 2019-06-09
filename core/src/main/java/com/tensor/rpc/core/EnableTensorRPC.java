package com.tensor.rpc.core;

import com.tensor.rpc.core.bean.factory.TensorBeanRegister;
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

    String server() default "";
    String ip() default "";
    int port() default 8080;

}
