package com.tensor.rpc.client.bean.factory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class TensorBeanRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(EnableTensorRPCBeanPostProcessor.class.getName(), beanDefinition(EnableTensorRPCBeanPostProcessor.class));
        registry.registerBeanDefinition(RpcServiceInjectBeanPostProcessor.class.getName(), beanDefinition(RpcServiceInjectBeanPostProcessor.class));
        registry.registerBeanDefinition(RpcRegisterInjectBeanPostProcessor.class.getName(), beanDefinition(RpcRegisterInjectBeanPostProcessor.class));
    }

    private BeanDefinition beanDefinition(Class<?> beanClass) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
        beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        return beanDefinitionBuilder.getBeanDefinition();
    }
}
