package com.tensor.rpc.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcRegister {

    /**
     *
     * @return {@link String}
     */
    String ip() default "127.0.0.1";

    /**
     *
     * @return
     */
    int port() default 8080;

    /**
     *
     * @return {@link String}
     */
    String serviceName() default "tensor-rpc";

    /**
     *
     * @return
     */
    Class<?> value();

}
