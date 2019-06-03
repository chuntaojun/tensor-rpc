package com.tensor.rpc.client.bean.factory;

import com.tensor.rpc.client.EnableTensorRPC;
import com.tensor.rpc.client.config.netty.NettyServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Component
public class EnableTensorRPCBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        EnableTensorRPC tensorRPC = bean.getClass().getAnnotation(EnableTensorRPC.class);
        if (tensorRPC == null) {
            return bean;
        } else {
            String serverAddr = tensorRPC.server();
            NettyServer.start(tensorRPC);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
