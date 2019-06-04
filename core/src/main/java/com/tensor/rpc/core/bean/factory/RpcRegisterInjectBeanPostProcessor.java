package com.tensor.rpc.core.bean.factory;

import com.tensor.rpc.core.cache.RpcInfoManager;
import com.tensor.rpc.core.cache.NativeMethodManager;
import com.tensor.rpc.common.annotation.RpcRegister;
import com.tensor.rpc.common.util.KeyBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Component
public class RpcRegisterInjectBeanPostProcessor implements BeanPostProcessor {

    /**
     * 接口服务注册
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class cls = bean.getClass();
        RpcRegister rpcRegister = (RpcRegister) cls.getAnnotation(RpcRegister.class);
        if (rpcRegister != null) {
            String serviceName = rpcRegister.serviceName();

            // 为该接口服务创建一个方法签名信息

            Class<?> registerType = rpcRegister.value();

            String key = KeyBuilder.buildServiceKey(serviceName, rpcRegister.ip(), rpcRegister.port());
            RpcInfoManager.providerRegister(registerType.getCanonicalName(), rpcRegister);
            NativeMethodManager.register(bean, cls);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
