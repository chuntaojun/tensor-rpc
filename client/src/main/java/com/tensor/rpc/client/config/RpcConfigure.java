package com.tensor.rpc.client.config;

import com.tensor.rpc.client.EnableTensorRPC;
import com.tensor.rpc.client.core.MethodExecutor;
import com.tensor.rpc.client.handler.BaseMethodExecutor;
import com.tensor.rpc.client.handler.NativeMethodExecutor;
import com.tensor.rpc.client.handler.RpcMethodExecutor;

/**
 * @author liaochuntao
 */

public class RpcConfigure {

    public volatile static RpcConfigure RPCCONFIGURE;

    private String serverAddr;
    private volatile boolean start = true;
    private final MethodExecutor methodExecutor;

    private RpcConfigure() {
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

    public static void init(EnableTensorRPC enableTensorRPC) {
        if (RPCCONFIGURE == null) {
            synchronized (RpcConfigure.class) {
                if (RPCCONFIGURE == null) {
                    RpcConfigure configure = new RpcConfigure();
                    configure.serverAddr = enableTensorRPC.ip() + ":" + enableTensorRPC.port();
                    RPCCONFIGURE = configure;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "RpcConfigure{" +
                "serverAddr='" + serverAddr + '\'' +
                '}';
    }

    public static MethodExecutor getMethodExecutor() {
        return RPCCONFIGURE.methodExecutor;
    }
}
