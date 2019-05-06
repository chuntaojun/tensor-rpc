package com.tensor.rpc.common.bean.factory;

import com.tensor.rpc.common.annotation.RpcService;
import com.tensor.rpc.common.proxy.filter.RpcServiceProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaochuntao
 */
public class RpcInjectBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {

    private static ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Object> serviceMap = new ConcurrentHashMap<>();

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(RpcService.class)) {
                RpcService rpcService = field.getAnnotation(RpcService.class);
                try {
                    field.set(bean, get(field.getName(), field, rpcService));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    private Object get(String s, Field field, RpcService rpcService) {
        return cache.computeIfAbsent(field.getName(), s1 -> RpcServiceProxy.inject(rpcService, field.getType()));
    }
}
