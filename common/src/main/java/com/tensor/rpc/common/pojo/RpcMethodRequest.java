package com.tensor.rpc.common.pojo;

import java.util.Map;

/**
 * @author liaochuntao
 */
public class RpcMethodRequest {

    private String methodName;
    private Map<Class, Object> param;
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

    public Map<Class, Object> getParam() {
        return param;
    }

    public void setParam(Map<Class, Object> param) {
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
}
