package com.tensor.rpc.client.handler.beat;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author liaochuntao
 */
public class BeatRecator {

    private static ConcurrentLinkedDeque<Map<String, String>> SYNC_DATA = new ConcurrentLinkedDeque<>();

    public static void push(Map<String, String> info) {
        SYNC_DATA.add(info);
    }

    public static Map<String, String> poll() {
        return SYNC_DATA.pollFirst();
    }

}
