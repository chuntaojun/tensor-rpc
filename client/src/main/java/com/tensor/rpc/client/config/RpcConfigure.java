package com.tensor.rpc.client.config;

/**
 * @author liaochuntao
 */
public class RpcConfigure {

    public volatile static RpcConfigure RPC_CONFIGURE;

    private String serverAddr;
    private String serviceName;
    private String exposeIp;
    private int exposePort;
    private volatile boolean start = true;

    public RpcConfigure() {
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getExposeIp() {
        return exposeIp;
    }

    public void setExposeIp(String exposeIp) {
        this.exposeIp = exposeIp;
    }

    public int getExposePort() {
        return exposePort;
    }

    public void setExposePort(int exposePort) {
        this.exposePort = exposePort;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public static void init(RpcConfigure configure) {
        if (RPC_CONFIGURE == null) {
            synchronized (RpcConfigure.class) {
                if (RPC_CONFIGURE == null) {
                    RPC_CONFIGURE = configure;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "RpcConfigure{" +
                "serverAddr='" + serverAddr + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", exposeIp='" + exposeIp + '\'' +
                ", exposePort=" + exposePort +
                '}';
    }
}
