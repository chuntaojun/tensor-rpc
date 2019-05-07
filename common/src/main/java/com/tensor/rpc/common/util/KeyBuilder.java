package com.tensor.rpc.common.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * @author liaochuntao
 */
public final class KeyBuilder {

    private static final String CONTACT = "@";

    public static String buildServiceKey(String ip, int port) {
        return ip + CONTACT + port;
    }

    public static String buildServiceKey(String serviceName, String ip, int port) {
        return serviceName + CONTACT + ip + CONTACT + port;
    }

    public static String methodSign(Method method) {
        String methodName = method.getName();
        Type[] params = method.getGenericParameterTypes();
        ArrayList<String> l = new ArrayList<>();
        l.add(methodName);
        for (Type param : params) {
            l.add(param.getTypeName());
        }
        return String.join("#", l);
    }

}
