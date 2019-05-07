package com.tensor.rpc.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcRegister {

    /**
     *
     * @return {@link String}
     */
    String serverAddr() default "127.0.0.1:8007";

    /**
     *
     * @return {@link String}
     */
    String serviceName();

}
