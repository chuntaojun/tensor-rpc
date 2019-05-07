package com.tensor.rpc.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;

/**
 * @author liaochuntao
 */
@Builder
@AllArgsConstructor
public class RpcMethodResponse {

    private String respId;
    private Throwable error;
    private Class<?> returnType;
    private String returnVal;

    public RpcMethodResponse() {
    }

    public String getRespId() {
        return respId;
    }

    public void setRespId(String respId) {
        this.respId = respId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public String getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(String returnVal) {
        this.returnVal = returnVal;
    }

    @Override
    public String toString() {
        return "RpcMethodResponse{" +
                "respId='" + respId + '\'' +
                ", error=" + error +
                ", returnType=" + returnType +
                ", returnVal=" + returnVal +
                '}';
    }
}
