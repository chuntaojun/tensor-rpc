package com.tensor.rpc.client.bean.factory;

import com.tensor.rpc.client.cache.CachePool;
import com.tensor.rpc.client.filter.RpcInjectProxy;
import com.tensor.rpc.client.config.RpcInit;
import com.tensor.rpc.common.annotation.RpcRegister;
import com.tensor.rpc.common.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

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
        if (cls.isAnnotationPresent(RpcInit.class)) {
            RpcInit rpcInit = cls.getAnnotation(RpcInit.class);
        } else if (cls.isAnnotationPresent(RpcRegister.class)) {
            RpcRegister rpcRegister = cls.getAnnotation(RpcRegister.class);
        } else {
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
        return bean;
    }

}
