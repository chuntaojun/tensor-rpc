package com.tensor.rpc.client.rpc.beat;

import com.tensor.rpc.client.schedule.RpcSchedule;
import com.tensor.rpc.common.pojo.HeartInfo;
import com.tensor.rpc.common.util.KeyBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.tensor.rpc.client.config.RpcConfigure.RPC_CONFIGURE;


/**
 * @author liaochuntao
 */
public class HeartBeatChannelHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        beat(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg is " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    private void beat(ChannelHandlerContext ctx) {
        synchronized (this) {
            if (RPC_CONFIGURE.isStart()) {
                RPC_CONFIGURE.setStart(false);
                HeartInfo heartInfo = new HeartInfo();
                String[] info = RPC_CONFIGURE.getServerAddr().split(":");
                heartInfo.setServerAddr(KeyBuilder.buildServiceKey(info[0], Integer.valueOf(info[1])));
                BeatTask task = new BeatTask(ctx, heartInfo);
                RpcSchedule.HeartExecutor.submit(task);
            }
        }
    }

    private static class BeatTask implements Runnable {

        private ChannelHandlerContext ctx;
        private final HeartInfo heartInfo;

        BeatTask(ChannelHandlerContext ctx, HeartInfo heartInfo) {
            this.ctx = ctx;
            this.heartInfo = heartInfo;
        }

        @Override
        public void run() {
            ctx.channel().writeAndFlush(heartInfo);
        }
    }

}
