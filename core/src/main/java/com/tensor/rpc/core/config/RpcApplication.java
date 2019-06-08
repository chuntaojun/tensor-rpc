package com.tensor.rpc.core.config;

import com.tensor.rpc.core.EnableTensorRPC;
import com.tensor.rpc.core.proxy.MethodExecutor;
import com.tensor.rpc.core.handler.BaseMethodExecutor;

import java.util.Map;

/**
 * @author liaochuntao
 */

public class RpcApplication {

    public volatile static RpcApplication RPCCONFIGURE;

    private String serverAddr;
    private String ip;
    private int port;
    private Map<Object, Object> metadata;
    private volatile boolean start = true;
    private final MethodExecutor methodExecutor;

    private RpcApplication() {
        methodExecutor = new BaseMethodExecutor();
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public static void init(EnableTensorRPC tensorRPC) {
        if (RPCCONFIGURE == null) {
            synchronized (RpcApplication.class) {
                if (RPCCONFIGURE == null) {
                    RpcApplication configure = new RpcApplication();
                    configure.ip = tensorRPC.ip();
                    configure.port = tensorRPC.port();
                    configure.serverAddr = tensorRPC.ip() + ":" + tensorRPC.port();
                    RPCCONFIGURE = configure;
                }
            }
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Map<Object, Object> getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "RpcApplication{" +
                "serverAddr='" + serverAddr + '\'' +
                '}';
    }

    public static MethodExecutor getMethodExecutor() {
        return RPCCONFIGURE.methodExecutor;
    }
}
