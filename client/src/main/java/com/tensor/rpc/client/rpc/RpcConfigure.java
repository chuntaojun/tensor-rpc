package com.tensor.rpc.client.rpc;

import com.tensor.rpc.client.EnableRpc;
import com.tensor.rpc.client.filter.MethodExecutor;
import com.tensor.rpc.client.filter.exec.BaseMethodExecutor;
import com.tensor.rpc.client.filter.exec.NativeMethodExecutor;
import com.tensor.rpc.client.filter.exec.RpcMethodExecutor;

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
        methodExecutor.nextChain(new NativeMethodExecutor()).nextChain(new RpcMethodExecutor());
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

    public static void init(EnableRpc enableRpc) {
        if (RPCCONFIGURE == null) {
            synchronized (RpcConfigure.class) {
                if (RPCCONFIGURE == null) {
                    RpcConfigure configure = new RpcConfigure();
                    configure.serverAddr = enableRpc.ip() + ":" + enableRpc.port();
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
