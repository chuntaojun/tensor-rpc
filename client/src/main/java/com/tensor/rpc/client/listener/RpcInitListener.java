package com.tensor.rpc.client.listener;

import com.tensor.rpc.client.cache.CachePool;
import com.tensor.rpc.client.cache.NaticeMethodPool;
import com.tensor.rpc.client.handler.beat.BeatReactor;
import com.tensor.rpc.client.EnableRpc;
import com.tensor.rpc.client.rpc.netty.NettyClient;
import com.tensor.rpc.client.rpc.netty.NettyServer;
import com.tensor.rpc.common.annotation.RpcRegister;
import com.tensor.rpc.common.util.KeyBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuples;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liaochuntao
 */
@Component
public class RpcInitListener implements ApplicationListener<ApplicationContextEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        String serverAddr = "";
        ApplicationContext context = event.getApplicationContext();
        Map<String, Object> data = context.getBeansWithAnnotation(EnableRpc.class);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            Class<?> cls = entry.getValue().getClass();
            EnableRpc enableRpc = cls.getAnnotation(EnableRpc.class);
            NettyServer.start(enableRpc);
            serverAddr = enableRpc.server();
            break;
        }

        Map<String, Object> providers = context.getBeansWithAnnotation(RpcRegister.class);
        Map<String, String> providerInfos = providers.values()
                .stream()
                .map(obj -> {
                    Class cls = obj.getClass();
                    RpcRegister rpcRegister = (RpcRegister) cls.getAnnotation(RpcRegister.class);
                    String serviceName = rpcRegister.serviceName();
                    String key = KeyBuilder.buildServiceKey(serviceName, rpcRegister.ip(), rpcRegister.port());
                    CachePool.register(cls.getCanonicalName(), rpcRegister);
                    NaticeMethodPool.register(obj, cls);
                    return Tuples.of(key, cls.getCanonicalName());
                })
                .collect(HashMap::new, (m, v) -> m.put(v.getT1(), v.getT2()), HashMap::putAll);
        if ("".equals(serverAddr)) {
            throw new RuntimeException("[Tensor RPC] : must set server addr");
        }

        BeatReactor.push(providerInfos);
        NettyClient.conServer(serverAddr);
    }

}
