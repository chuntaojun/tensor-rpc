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
    String ip();

    /**
     *
     * @return
     */
    int port();

    /**
     *
     * @return {@link String}
     */
    String serviceName();

    /**
     *
     * @return
     */
    Class<?> value();

}
