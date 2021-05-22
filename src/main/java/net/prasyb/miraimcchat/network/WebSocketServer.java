package net.prasyb.miraimcchat.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.mamoe.mirai.utils.MiraiLogger;
import net.prasyb.miraimcchat.JavaPluginMain;

public class WebSocketServer extends Thread {
    private final MiraiLogger logger = JavaPluginMain.INSTANCE.getLogger();
    private final int port;

    public WebSocketServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        logger.info("正在启动webSocket服务器");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new WebSocketChannelInitializer());
            Channel channel = bootstrap.bind(port).sync().channel();
            logger.info("webSocket服务器启动成功: " + channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.warning("运行出错或线程被正常中断: " + e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            logger.info("webSocket服务器已关闭");
        }
    }
}