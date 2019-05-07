package com.tensor.rpc.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcService {

    String serviceName() default "";
    String ip() default "";
    int port() default 8080;

}
