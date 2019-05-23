package com.tensor.rpc.client.rpc.netty;

import com.tensor.rpc.client.handler.RpcMethodResponseHandler;
import com.tensor.rpc.client.handler.beat.HeartBeatChannelHandler;
import com.tensor.rpc.client.EnableRpc;
import com.tensor.rpc.client.rpc.RpcConfigure;
import com.tensor.rpc.common.serialize.kryo.KryoDecoder;
import com.tensor.rpc.common.serialize.kryo.KryoEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liaochuntao
 */
public class NettyServer {

    private static ExecutorService service = Executors.newSingleThreadExecutor();

    public static void start(EnableRpc enableRpc) {
        int port = enableRpc.port();
        RpcConfigure.init(enableRpc);
        service.submit(new Server(port));
    }

    private static void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                            ch.pipeline().addLast(new KryoDecoder(10 * 1024 * 1024));
                            ch.pipeline().addLast(new KryoEncoder());
                            ch.pipeline().addLast(new HeartBeatChannelHandler());
                            ch.pipeline().addLast(new RpcMethodResponseHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("[Tensor RPC] : server has started...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static class Server implements Runnable {

        private int port;

        Server(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            bind(port);
        }
    }

}

