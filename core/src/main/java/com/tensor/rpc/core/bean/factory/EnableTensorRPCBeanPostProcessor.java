package com.tensor.rpc.core.bean.factory;

import com.tensor.rpc.common.constants.RpcType;
import com.tensor.rpc.core.EnableTensorRPC;
import com.tensor.rpc.core.config.RpcApplication;
import com.tensor.rpc.core.config.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Objects;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Slf4j
public class EnableTensorRPCBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        EnableTensorRPC rpc = bean.getClass().getAnnotation(EnableTensorRPC.class);
        if (rpc == null) {
            return bean;
        } else {
            RpcApplication.init(rpc);
            if (Objects.equals(rpc.type(), RpcType.PROVIDER)) {
                log.info("[TENSOR RPC] work type is provider");
                NettyServer.start(rpc);
            } else {
                log.info("[TENSOR RPC] work type is consumer");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
