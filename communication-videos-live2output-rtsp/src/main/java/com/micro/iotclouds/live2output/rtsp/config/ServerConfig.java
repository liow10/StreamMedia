package com.micro.iotclouds.live2output.rtsp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;

import lombok.extern.slf4j.Slf4j;

//@Configuration
//@ComponentScan(basePackages = { "com.darkmi" })
//@PropertySource("classpath:application.properties")
@Slf4j
public class ServerConfig {
//	public static final Logger logger = Logger.getLogger(ServerConfig.class);
	private static final String PROPERTY_RTSP_SERVER_IP = "rtsp.server.ip";
	private static final String PROPERTY_RTSP_SERVER_PORT = "rtsp.server.port";
	private static final String PROPERTY_RTSP_IDLE_TIME = "rtsp.idle.time";
	private static final String PROPERTY_RTCP_IDLE_TIME = "rtcp.idle.time";
	private static final String PROPERTY_RTP_IDLE_TIME = "rtp.idle.time";
	private static final String PROPERTY_EXECUTOR_THREADPOOL = "executor.threadpool";
	@Autowired
	private Environment environment;

//	@Bean(autowire = Autowire.BY_TYPE)
//	public RtspController rtspServer() {
//		String ip = environment.getProperty(PROPERTY_RTSP_SERVER_IP);
//		int port = Integer.parseInt(environment.getProperty(PROPERTY_RTSP_SERVER_PORT));
//
//		RtspController rtspController = null;
//		try {
//			rtspController = new RtspController();
//			rtspController.setIp(ip);
//			rtspController.setPort(port);
//		} catch (Exception e) {
//			log.error("Create RtspServer Error.........", e);
//		}
//		return rtspController;
//	}

	public String getIp() {
		//return environment.getProperty(PROPERTY_RTSP_SERVER_IP);
		return "127.0.0.1";
	}

	public int getPort() {
		//return Integer.parseInt(environment.getProperty(PROPERTY_RTSP_SERVER_PORT));
		return MyLiveConfig.getInstance().getRtspPort();
	}

	public int getRtspIdleTime() {
		return Integer.parseInt(environment.getProperty(PROPERTY_RTSP_IDLE_TIME));
	}
	
	public int getExecutorThreadpool() {
		return Integer.parseInt(environment.getProperty(PROPERTY_EXECUTOR_THREADPOOL));
	}
}
