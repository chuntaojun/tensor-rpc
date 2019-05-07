package com.tensor.rpc.client.schedule;

import java.util.concurrent.*;

/**
 * @author liaochuntao
 */
public final class RpcSchedule {

    public static class HeartExecutor {

        private static ThreadFactory HeartBeatFactory = new ThreadFactory() {
            private final String namePrefix = "Tensor-RPC-HEART-THREAD-";

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

    public static class RpcExecutor {

        private static ThreadFactory RpcFactory = new ThreadFactory() {
            private final String namePrefix = "Tensor-RPC-THREAD-";

            @Override
            public Thread newThread(Runnable r) {
                String name = namePrefix;
                return new Thread(r, name);
            }
        };

        private static final ExecutorService RPC = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), RpcFactory);

        public static void submit(Runnable runnable) {
            RPC.submit(runnable);
        }

    }

}
