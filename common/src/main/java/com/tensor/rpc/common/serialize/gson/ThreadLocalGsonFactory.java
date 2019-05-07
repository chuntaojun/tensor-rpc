package com.tensor.rpc.common.serialize.gson;

import com.google.gson.Gson;

class ThreadLocalGsonFactory {

    private static final ThreadLocal<Gson> GSON_THREAD_LOCAL = ThreadLocal.withInitial(() -> new Gson());

    public static Gson gson() {
        return GSON_THREAD_LOCAL.get();
    }

}
