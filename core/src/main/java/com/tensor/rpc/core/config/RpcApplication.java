package com.tensor.rpc.core.config;

import com.tensor.rpc.core.EnableTensorRPC;
import com.tensor.rpc.core.handler.MethodInvoker;

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
    private final MethodInvoker methodInvoker;

    private RpcApplication() {
        methodInvoker = new MethodInvoker();
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

    public static void init(EnableTensorRPC rpc) {
        if (RPCCONFIGURE == null) {
            synchronized (RpcApplication.class) {
                if (RPCCONFIGURE == null) {
                    RpcApplication configure = new RpcApplication();
                    configure.ip = rpc.ip();
                    configure.port = rpc.port();
                    configure.serverAddr = rpc.ip() + ":" + rpc.port();
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

    public static MethodInvoker getMethodInvoker() {
        return RPCCONFIGURE.methodInvoker;
    }
}
