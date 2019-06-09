package com.tensor.rpc.common.annotation;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcService {

    /**
     * 采用注册中心时按服务名调用
     *
     * @return <code>tensor-rpc</code>
     */
    String serviceName() default "tensor-rpc";

    /**
     * 远程接口ip地址 {@link String}
     *
     * @return <code>127.0.0.1</code>
     */
    String ip() default "127.0.0.1";

    /**
     * 远程接口 {@link Integer}
     *
     * @return <code>8080</code>
     */
    int port() default 8080;

    /**
     * 本地方法实现优先调用
     *
     * @return <code>true</code>
     */
    boolean localFirst() default true;

}
