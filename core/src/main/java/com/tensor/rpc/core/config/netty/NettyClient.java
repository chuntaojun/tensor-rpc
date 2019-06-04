package com.tensor.rpc.core.config.netty;

import com.tensor.rpc.core.handler.RpcMethodRequestHandler;
import com.tensor.rpc.common.serialize.kryo.KryoDecoder;
import com.tensor.rpc.common.serialize.kryo.KryoEncoder;
import com.tensor.rpc.common.util.KeyBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liaochuntao
 */
@Slf4j
public class NettyClient {

    private static final ConcurrentHashMap<String, ChannelPool> CHANNEL_POOL = new ConcurrentHashMap<>();

    private NettyClient() {}

    public static void conServer(String serverAddr) {
        try {
            String[] s = serverAddr.split(":");
            String ip = s[0];
            int port = Integer.valueOf(s[1]);
            open(port, ip);
        } catch (Exception ignore) {

        }
    }

    public static Channel getConnection(String serviceName, String host, int port) throws InterruptedException {
        String key = KeyBuilder.buildServiceKey(serviceName, host, port);
        ChannelPool pool = CHANNEL_POOL.computeIfAbsent(key, s -> {
            ChannelPool pool1 = new ChannelPool();
            while (!pool1.isFull()) {
                try {
                    pool1.push(open(port, host));
                } catch (InterruptedException e) {
                    log.error("[Tensor-RPC ERROR] : {}", e.getMessage());
                }
            }
            return pool1;
        });
        return pool.poll();
    }

    public static void release(Channel channel, String serviceName, String host, int port) {
        String key = KeyBuilder.buildServiceKey(serviceName, host, port);
        CHANNEL_POOL.get(key).push(channel);
    }

    private static Channel open(int port, String host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        ch.pipeline().addLast(new KryoDecoder(10 * 1024 * 1024));
                        ch.pipeline().addLast(new KryoEncoder());
                        ch.pipeline().addLast(new RpcMethodRequestHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
        return future.sync().channel();
    }

    private static class ChannelPool {
        private final int capacity = 2;
        private Channel[] channels = new Channel[capacity];
        private Lock lock = new ReentrantLock();
        private Condition cond = lock.newCondition();
        private int front;
        private int rear;
        private volatile int size;
        private boolean isAlive = false;

        ChannelPool() {
            this.front = 0;
            this.rear = 0;
            this.size = 0;
            this.isAlive = true;
        }

        boolean isFull() {
            return isAlive && (rear + 1) % capacity == front;
        }

        void push(Channel channel) {
            final Lock lock = this.lock;
            lock.lock();
            if (isFull()) {
                return;
            }
            channels[rear] = channel;
            rear = (rear + 1) % capacity;
            cond.signalAll();
            lock.unlock();
        }

        Channel poll() throws InterruptedException {
            final Lock lock = this.lock;
            lock.lock();
            if (rear == front) {
                System.out.println("lock here");
                cond.await();
            }
            Channel channel = channels[front];
            front = (front + 1) % capacity;
            lock.unlock();
            return channel;
        }

        void shutdown() {
            final Lock lock = this.lock;
            lock.lock();
            isAlive = false;
            lock.unlock();
        }
    }

}
