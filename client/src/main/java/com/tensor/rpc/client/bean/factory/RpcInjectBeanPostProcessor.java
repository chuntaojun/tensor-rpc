package com.tensor.rpc.client.bean.factory;

import com.tensor.rpc.client.cache.CachePool;
import com.tensor.rpc.client.filter.RpcInjectProxy;
import com.tensor.rpc.common.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author liaochuntao
 */
@Component
public class RpcInjectBeanPostProcessor implements BeanPostProcessor {

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
                    CachePool.register(field.getType().getCanonicalName(), rpcService);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
