package com.tensor.rpc.client.handler.beat;

import com.tensor.rpc.client.schedule.RpcSchedule;
import com.tensor.rpc.common.pojo.HeartInfo;
import com.tensor.rpc.common.util.KeyBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.tensor.rpc.client.rpc.RpcConfigure.RPCCONFIGURE;


/**
 * @author liaochuntao
 */
public class HeartBeatChannelHandler extends SimpleChannelInboundHandler<HeartInfo> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        beat(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartInfo msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    private void beat(ChannelHandlerContext ctx) {
        if (RPCCONFIGURE.isStart()) {
            synchronized (this) {
                if (RPCCONFIGURE.isStart()) {
                    RPCCONFIGURE.setStart(false);
                    String[] info = RPCCONFIGURE.getServerAddr().split(":");

                    HeartInfo heartInfo = new HeartInfo();
                    heartInfo.setServerAddr(KeyBuilder.buildServiceKey(info[0], Integer.valueOf(info[1])));
                    heartInfo.setData(BeatReactor.poll());

                    RpcSchedule.HeartExecutor.submit(new BeatTask(ctx, heartInfo));
                }
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
