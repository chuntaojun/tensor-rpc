package com.tensor.rpc.core.bean.factory;

import com.tensor.rpc.core.config.ApplicationManager;
import com.tensor.rpc.core.proxy.RpcInjectProxy;
import com.tensor.rpc.common.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @author liaochuntao
 */
public class RpcServiceInjectBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        rpcServiceInject(cls, bean);
        return bean;
    }

    /**
     * 接口进行代理，对接口的所有操作转为 RPC 远程调用操作
     *
     * @param cls {@link Class} interface type
     * @param bean {@link Object} Spring's bean
     */
    private void rpcServiceInject(Class cls, Object bean) {
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(RpcService.class)) {
                RpcService rpcService = field.getAnnotation(RpcService.class);
                try {
                    field.set(bean, RpcInjectProxy.inject(rpcService, field.getType()));
                    ApplicationManager.getRpcInfoManager().consumerRegister(field.getType().getCanonicalName(), rpcService);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
