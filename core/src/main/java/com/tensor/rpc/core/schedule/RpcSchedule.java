package com.tensor.rpc.core.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author liaochuntao
 */
@Slf4j
public final class RpcSchedule {

    private static final int MIN_POO_SIE = 2;
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 20;
    private static final long KEEP_ALIVE_TIME = 10;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;

    private static RejectedExecutionHandler rejectedExecutionHandler =
            (r, executor) -> log.error("{} Rpc 任务被拒绝。 {}", r.toString(), executor.toString());

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

        public static final ThreadPoolExecutor RPC = new ThreadPoolExecutor(MIN_POO_SIE, MIN_POO_SIE, KEEP_ALIVE_TIME, UNIT,
                new ArrayBlockingQueue<>(1000), RpcFactory, rejectedExecutionHandler);

        public static void submit(Runnable runnable) {
            RPC.submit(runnable);
        }

    }

}
