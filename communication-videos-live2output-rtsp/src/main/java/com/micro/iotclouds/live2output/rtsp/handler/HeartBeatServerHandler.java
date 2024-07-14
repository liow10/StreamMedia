package com.micro.iotclouds.live2output.rtsp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.micro.iotclouds.live2output.rtsp.config.ServerConfig;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 
 * @author zhouyinfei
 *
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
	public static final Logger log = LoggerFactory.getLogger(HeartBeatServerHandler.class); 
	public final ServerConfig serverConfig;
	public HeartBeatServerHandler(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

		@Override
	    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
	    	log.debug("已经{}秒未收到或未发送给客户端的消息了！",serverConfig.getRtspIdleTime());
	        
	        if (evt instanceof IdleStateEvent){
	            IdleStateEvent event = (IdleStateEvent)evt;
	            if (event.state()== IdleState.ALL_IDLE){
                    log.debug("关闭这个不活跃通道！");
                    ctx.channel().close();
	            }
	        }else {
	            super.userEventTriggered(ctx,evt);
	        }
	    }

	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        log.debug("client says: {}", msg.toString());
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        ctx.close();
	    }

}
