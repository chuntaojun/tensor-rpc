package com.tensor.rpc.common.pojo;

/**
 * @author liaochuntao
 */
public class HeartInfo {

    private long lastSendTime = System.currentTimeMillis();
    private String serverAddr;

    public HeartInfo() {
    }

    public HeartInfo(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    @Override
    public String toString() {
        return "HeartInfo{" +
                "lastSendTime=" + lastSendTime +
                ", serverAddr='" + serverAddr + '\'' +
                '}';
    }
}
