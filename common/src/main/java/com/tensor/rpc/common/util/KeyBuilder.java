package com.tensor.rpc.common.util;

/**
 * @author liaochuntao
 */
public final class KeyBuilder {

    private static final String CONTACT = "@";

    public static String buildServiceKey(String serviceName, String ip, int port) {
        return serviceName + CONTACT + ip + CONTACT + port;
    }

}
