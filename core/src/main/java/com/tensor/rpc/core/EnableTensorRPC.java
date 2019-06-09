package com.tensor.rpc.core;

import com.tensor.rpc.common.constants.RpcType;
import com.tensor.rpc.core.bean.factory.TensorBeanRegister;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import(value = {TensorBeanRegister.class})
@Inherited
public @interface EnableTensorRPC {

    /**
     *
     * @return
     */
    String server() default "";

    /**
     *
     * @return
     */
    String ip() default "";

    /**
     *
     * @return
     */
    int port() default 8080;

    /**
     * 默认使用模式为 {@link RpcType#PROVIDER}
     *
     * @return {@link RpcType#PROVIDER}
     */
    RpcType type() default RpcType.PROVIDER;

}
