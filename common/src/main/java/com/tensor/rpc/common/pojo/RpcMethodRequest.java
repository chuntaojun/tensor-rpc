package com.tensor.rpc.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Arrays;

/**
 * @author liaochuntao
 */
@Builder
@AllArgsConstructor
public class RpcMethodRequest {

    private String methodName;
    private Object[] param;
    private Class returnType;
    private Object returnValue;
    private Exception exception;

    public RpcMethodRequest() {
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "RpcMethodRequest{" +
                "methodName='" + methodName + '\'' +
                ", param=" + Arrays.toString(param) +
                ", returnType=" + returnType +
                ", returnValue=" + returnValue +
                ", exception=" + exception +
                '}';
    }
}
