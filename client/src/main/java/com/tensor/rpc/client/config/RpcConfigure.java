package com.tensor.rpc.client.config;

/**
 * @author liaochuntao
 */

public class RpcConfigure {

    public volatile static RpcConfigure RPC_CONFIGURE;

    private String serverAddr;
    private volatile boolean start = true;

    private RpcConfigure() {}

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

    public static void init(RpcInit rpcInit) {
        if (RPC_CONFIGURE == null) {
            synchronized (RpcConfigure.class) {
                if (RPC_CONFIGURE == null) {
                    RpcConfigure configure = new RpcConfigure();
                    configure.serverAddr = rpcInit.serverAddr();

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
}
