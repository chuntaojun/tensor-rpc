package com.tensor.rpc.common.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liaochuntao
 */
public class HeartInfo {

    private long lastSendTime = System.currentTimeMillis();
    private String serverAddr;
    private Map<String, String> data = new HashMap<>();

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

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HeartInfo{" +
                "lastSendTime=" + lastSendTime +
                ", serverAddr='" + serverAddr + '\'' +
                ", data=" + data +
                '}';
    }
}
