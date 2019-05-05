package com.tensor.rpc.client.config;

import com.tensor.rpc.client.rpc.HeartBeatChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author liaochuntao
 */
public class NettyClientConfigure {

    private static final int READ_IDEL_TIME_OUT = 4;
    private static final int WRITE_IDEL_TIME_OUT = 5;
    private static final int ALL_IDEL_TIME_OUT = 7;

    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port, String host) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                                WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new HeartBeatChannelHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            System.out.println("Client has started...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
