package com.micro.iotclouds.modules.rtmp.server.server;

import javax.annotation.PostConstruct;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.springframework.stereotype.Component;

import com.micro.iotclouds.communication.video.core.domain.MyLiveConfig;
import com.micro.iotclouds.communication.video.core.manager.StreamManager;
import com.mkst.iotclouds.communication.protocol.websocket.server.WssMediaServer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author longyubo 2020年1月7日 下午3:02:39
 **/
@Slf4j
/*
 * @Component
 * 
 * @Order(5)
 */
@Component
public class MyLiveServer/* implements ApplicationRunner */ {

//	private static final Logger LOGGER = LoggerFactory.getLogger(MyLiveServer.class);

//	@Autowired
//	private static ICommDeviceProtocolsService commDeviceProtocolsService;
//
////    @Autowired
//	private static RedisUtil4Protocol redisUtil4Protocol = SpringUtil2.getBean(RedisUtil4Protocol.class);

//	private static String running = Global.getConfig("rtmp.running");
//	public static void main(String[] args) throws Exception {
//		startLiveServer();
//	}

//	@Autowired
//	private MyLiveConfig INSTANCE;// = SpringUtil.getBean(MyLiveConfig.class);

	/**
	 * @throws Exception
	 */
	public void startLiveServer() throws Exception {
		boolean readConfig = readConfig();
		if (!readConfig) {
			log.info("Initializing {}  RTMP Server ...\", \"请先配置RTMP的协议信息。");
			System.out.println("Initializing {}  RTMP Server ...\", \"请先配置RTMP的协议信息。");
			// return;
			throw new RuntimeException("Initializing {}  RTMP Server ...\\\", \\\"请先配置RTMP的协议信息。");
		}
		StreamManager streamManager = new StreamManager();

		int rtmpPort = MyLiveConfig.getInstance().getRtmpPort();
		int handlerThreadPoolSize = MyLiveConfig.getInstance().getHandlerThreadPoolSize();

		RTMPServer rtmpServer = new RTMPServer(rtmpPort, streamManager, handlerThreadPoolSize);
		rtmpServer.run();

		if (!MyLiveConfig.getInstance().isEnableHttpFlv()) {
			return;
		}

		// 启动http-flv服务
		int httpPort = MyLiveConfig.getInstance().getHttpFlvPort();
		HttpFlvServer httpFlvServer = new HttpFlvServer(httpPort, streamManager, handlerThreadPoolSize);
		// httpFlvServer.run();
		Thread objHttpFlvServer = new Thread(httpFlvServer);
		objHttpFlvServer.start();

		// 启动rtsp服务
//		int rtspPort = MyLiveConfig.getInstance().getRtspPort();
//		RtspNewServer rtspServer = new RtspNewServer(rtspPort, streamManager, handlerThreadPoolSize);
//		// rtspServer.run();
//		Thread objRtspNewServer = new Thread(rtspServer);
//		objRtspNewServer.start();
		// 启动rtsp服务
//		int rtspPort = MyLiveConfig.getInstance().getRtspPort();
//		RtspServerStart rtspServer = new RtspServerStart(rtspPort, streamManager, handlerThreadPoolSize);
//		// rtspServer.run();
//		Thread objRtspNewServer = new Thread(rtspServer);
//		objRtspNewServer.start();

		// 启动wss-flv服务
		int wssPort = MyLiveConfig.getInstance().getWssPort();
		WssMediaServer wssServer = new WssMediaServer(wssPort, streamManager, handlerThreadPoolSize);
		// wssServer.run();
		Thread objWssMediaServer = new Thread(wssServer);
		objWssMediaServer.start();

		// int wssPort = INSTANCE.getWssPort();
		// Http2FlvServer objHttp2FlvServer = new Http2FlvServer(wssPort, streamManager,
		// handlerThreadPoolSize);
		// objHttp2FlvServer.run();
	}

	@PostConstruct
	public void init() throws Exception {
		// try {
		startLiveServer();
		log.info("rtmpServer启动");
		// }catch(Exception e) {
		// log.info("rtmpServer启动失败："+e.getMessage());
		// }
		// 提前初始化，可避免推拉流启动耗时太久
		loadFFmpeg();
	}

	/**
	 * 提前初始化，可避免推拉流启动耗时太久
	 */
//	@PostConstruct
	private void loadFFmpeg() {
		try {
			log.info("正在初始化资源，请稍等...");
			FFmpegFrameGrabber.tryLoad();
			FFmpegFrameRecorder.tryLoad();
			log.info("初始化成功");
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	private static boolean readConfig() {
//		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//		try {
//			File file = new File("./mylive.yaml");
//
//			MyLiveConfig cfg = mapper.readValue(file, class);
//			log.info("MyLive read configuration as : {}", cfg);
//
//			INSTANCE = cfg;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//
//		CommDeviceProtocols deviceProtocols = new CommDeviceProtocols();
//		deviceProtocols.setProtocolsCode(DeviceProtocolsEnums.DEVICE_PROTOCOLS_RTMP.getCode());
//		List<CommDeviceProtocols> lstCommDeviceProtocols =  SpringUtils.getBean(CommDeviceProtocolsServiceImpl.class).selectDeviceProtocolsList(deviceProtocols);
//		Object objCommDeviceProtocols = redisUtil4Protocol.get(CommDeviceProtocols.COMM_DEVICE_PROTOCOLS_KEY);
//		if (objCommDeviceProtocols == null) {
//			log.info("Initializing {} RTMP Server ...", "请先配置RTMP的协议信息。");
//			throw new RuntimeException("Initializing {}  RTMP Server ...\\\", \\\"请先配置RTMP的协议信息。");
//		}
//		@SuppressWarnings("unchecked")
//		List<CommDeviceProtocols> lstCommDeviceProtocols = (List<CommDeviceProtocols>) objCommDeviceProtocols;
//		if (lstCommDeviceProtocols == null || lstCommDeviceProtocols.size() == 0) {
//			log.info("Initializing {} RTMP Server ...", "请先配置RTMP的协议信息。");
//			throw new RuntimeException("Initializing {}  RTMP Server ...\\\", \\\"请先配置RTMP的协议信息。");
//		}
//		CommDeviceProtocols newCommDeviceProtocols = lstCommDeviceProtocols.get(0);
//		if (!newCommDeviceProtocols.getIsAutostart().equals("0")) {
//			log.info("Initializing {}  RTMP Server ...\", \"请先配置RTMP的协议信息。");
//			throw new RuntimeException("Initializing {}  RTMP Server ...\\\", \\\"请先配置RTMP的协议信息。");
//		}
//		brokerProperties = JSONUtil.toBean(newCommDeviceProtocols.getProtocolsParameter(), BrokerProperties.class);
//		INSTANCE = JSON.parseObject(newCommDeviceProtocols.getProtocolsParameter(), class);
		// BeanUtil.copyProperties(class,INSTANCE);
		return true;
	}

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		init();
//	}
}
