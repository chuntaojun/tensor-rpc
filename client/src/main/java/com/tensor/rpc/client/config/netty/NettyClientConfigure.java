package com.tensor.rpc.client.config.netty;

import com.tensor.rpc.client.rpc.RpcMethodHandler;
import com.tensor.rpc.client.rpc.beat.HeartBeatChannelHandler;
import com.tensor.rpc.common.serialize.KryoDecoder;
import com.tensor.rpc.common.serialize.KryoEncoder;
import com.tensor.rpc.common.util.KeyBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liaochuntao
 */
public class NettyClientConfigure {

    private static final ConcurrentHashMap<String, ChannelPool> CHANNEL_POOL = new ConcurrentHashMap<>();

    public NettyClientConfigure() {
    }

    public Channel getConnection(String serviceName, String host, int port) throws InterruptedException {
        String key = KeyBuilder.buildServiceKey(serviceName, host, port);
        ChannelPool pool = CHANNEL_POOL.computeIfAbsent(key, s -> {
            ChannelPool pool1 = new ChannelPool();
            while (!pool1.isFull()) {
                try {
                    pool1.push(connect(port, host));
                } catch (InterruptedException ignored) {
                }
            }
            return pool1;
        });
        return pool.poll();
    }

    private Channel connect(int port, String host) throws InterruptedException {
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
                        ch.pipeline().addLast(new HeartBeatChannelHandler());
                        ch.pipeline().addLast(new RpcMethodHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
        return future.sync().channel();
    }

    private static class ChannelPool {
        private final int capacity = 1;
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
            this.isAlive = true;
        }

        boolean isFull() {
            return isAlive && capacity == size;
        }

        void push(Channel channel) {
            final Lock lock = this.lock;
            lock.lock();
            size ++;
            if ((rear + 1) % capacity == front) {
                return;
            }
            channels[rear] = channel;
            rear ++;
            cond.signal();
            lock.unlock();
        }

        Channel poll() throws InterruptedException {
            final Lock lock = this.lock;
            lock.lock();
            if (rear == front) {
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
