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

}
