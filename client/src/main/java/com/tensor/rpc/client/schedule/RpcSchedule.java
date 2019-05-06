package com.tensor.rpc.client.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author liaochuntao
 */
public final class RpcSchedule {

    public static class HeartExecutor {

        private static ThreadFactory HeartBeatFactory = new ThreadFactory() {
            private final String namePrefix = "Tensor-RPC-HEART_THREAD-";

            @Override
            public Thread newThread(Runnable r) {
                String name = namePrefix;
                return new Thread(r, name);
            }
        };

        private static final ScheduledExecutorService HEART_BEAT = Executors.newSingleThreadScheduledExecutor(HeartBeatFactory);

        public static void submit(Runnable runnable) {
            HEART_BEAT.scheduleAtFixedRate(runnable, 30, 15, TimeUnit.SECONDS);
        }

    }

}
