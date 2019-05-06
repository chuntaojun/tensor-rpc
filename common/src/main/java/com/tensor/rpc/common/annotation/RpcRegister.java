package com.tensor.rpc.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
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
