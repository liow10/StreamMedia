package com.micro.iotclouds.communication.video.core.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.micro.iotclouds.communication.video.core.utils.SpringUtils;

/**
 * @author longyubo 2020年1月9日 下午2:29:25
 **/
//@Data
@Component
//@Configuration
public class MyLiveConfig {

//	public static MyLiveConfig INSTANCE = new MyLiveConfig();

//	int rtmpPort = Integer.parseInt(Global.getConfig("rtmp.rtmpPort"));
//	int httpFlvPort = Integer.parseInt(Global.getConfig("rtmp.httpFlvPort"));
//	boolean saveFlvFile = Boolean.parseBoolean(Global.getConfig("rtmp.saveFlvFile"));
//	String saveFlVFilePath = Global.getConfig("rtmp.saveFlVFilePath");
//	int handlerThreadPoolSize = Integer.parseInt(Global.getConfig("rtmp.handlerThreadPoolSize"));
//	boolean enableHttpFlv = Boolean.parseBoolean(Global.getConfig("rtmp.enableHttpFlv"));

	@Value("${communication.isHaproxy}")
	private boolean isHaproxy;

	@Value("${communication.rtmp.running}")
	private boolean running;// = Boolean.parseBoolean(Global.getConfig("communication.rtmp.running"));

	@Value("${communication.rtmp.ffmpegWindow}")
	private String ffmpegWindow;// = Global.getConfig("communication.rtmp.ffmpegWindow");

	@Value("${communication.rtmp.ffmpegUnix}")
	private String ffmpegUnix;// = Global.getConfig("communication.rtmp.ffmpegUnix");

	@Value("${communication.rtmp.ffmpegLiveStream}")
	private String ffmpegLiveStream;// = Global.getConfig("communication.rtmp.ffmpegLiveStream");

	@Value("${communication.rtmp.ffmpegPicPath}")
	private String ffmpegPicPath;// = Global.getConfig("communication.rtmp.ffmpegPicPath");

	@Value("${communication.rtmp.taskReconnectInterval}")
	private int taskReconnectInterval;// =
	// Integer.parseInt(Global.getConfig("communication.rtmp.taskReconnectInterval"));

	@Value("${communication.rtmp.taRkreconnectMaxTimes}")
	private int taRkreconnectMaxTimes;// =
	// Integer.parseInt(Global.getConfig("communication.rtmp.taRkreconnectMaxTimes"));

	@Value("${communication.rtmp.threadCorePoolSize}")
	private int threadCorePoolSize;// =
	// Integer.parseInt(Global.getConfig("communication.rtmp.threadCorePoolSize"));

	@Value("${communication.rtmp.threadMaximumPoolSize}")
	private int threadMaximumPoolSize;// =
	// Integer.parseInt(Global.getConfig("communication.rtmp.threadMaximumPoolSize"));

	@Value("${communication.rtmp.threadKeepAliveTime}")
	private int threadKeepAliveTime;// =
	// Integer.parseInt(Global.getConfig("communication.rtmp.threadKeepAliveTime"));

	@Value("${communication.rtmp.rtmpPort}")
	private int rtmpPort;// = Integer.parseInt(Global.getConfig("communication.rtmp.rtmpPort"));

	@Value("${communication.rtmp.enableHttpFlv}")
	private boolean enableHttpFlv;// = Boolean.parseBoolean(Global.getConfig("communication.rtmp.enableHttpFlv"));

	@Value("${communication.rtmp.httpFlvPort}")
	private int httpFlvPort;// = Integer.parseInt(Global.getConfig("communication.rtmp.httpFlvPort"));

	@Value("${communication.rtsp.port}")
	private int rtspPort;// = Integer.parseInt(Global.getConfig("communication.rtsp.port"));

	@Value("${communication.wss.wssPort}")
	private int wssPort;// = Integer.parseInt(Global.getConfig("communication.wss.wssPort"));

	// 是否保存文件
	@Value("${communication.rtmp.saveFlvFile}")
	private boolean saveFlvFile;// = Boolean.parseBoolean(Global.getConfig("communication.rtmp.saveFlvFile"));

	// 保存文件路径
	@Value("${communication.rtmp.saveFlVFilePath}")
	private String saveFlVFilePath;// = Global.getConfig("communication.rtmp.saveFlVFilePath");

	// 是否保存文件
	@Value("${communication.rtmp.saveMp4File}")
	private boolean saveMp4File;// = Boolean.parseBoolean(Global.getConfig("communication.rtmp.saveFlvFile"));

	// 保存文件路径
	@Value("${communication.rtmp.saveMp4FilePath}")
	private String saveMp4FilePath;// = Global.getConfig("communication.rtmp.saveFlVFilePath");

	@Value("${communication.rtmp.handlerThreadPoolSize}")
	private int handlerThreadPoolSize;// =
	// Integer.parseInt(Global.getConfig("communication.rtmp.handlerThreadPoolSize"));
	// 保存文件时间间隔(小时)
	@Value("${communication.rtmp.saveDataTimeInterval}")
	private Double saveDataTimeInterval;// = Double
	// .parseDouble(Global.getConfig("communication.rtmp.saveDataTimeInterval"));
	// 删除文件时间间隔(小时)
	@Value("${communication.rtmp.deleteDataTimeInterval}")
	private Double deleteDataTimeInterval;// = Double
	// .parseDouble(Global.getConfig("communication.rtmp.deleteDataTimeInterval"));
	// 删除过期文件(小时)
	@Value("${communication.rtmp.deleteDataHour}")
	private Double deleteDataHour;// = Double.parseDouble(Global.getConfig("communication.rtmp.deleteDataHour"));
	// redis
	@Value("${communication.rtmp.redisAddr}")
	private String redisAddr;// = Global.getConfig("communication.rtmp.redisAddr");
	@Value("${communication.rtmp.redisAuth}")
	private String redisAuth;// = Global.getConfig("communication.rtmp.redisAuth");

	@Value("${communication.rtmp.iotLinkip}")
	private String iotLinkip;// = Global.getConfig("communication.rtmp.iotLinkip");
	@Value("${communication.rtmp.iotLinkport}")
	private String iotLinkport;// = Global.getConfig("communication.rtmp.iotLinkport");
	@Value("${communication.rtmp.iotLink}")
	private String iotLink;// = Global.getConfig("communication.rtmp.iotLink");

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getFfmpegWindow() {
		return ffmpegWindow;
	}

	public void setFfmpegWindow(String ffmpegWindow) {
		this.ffmpegWindow = ffmpegWindow;
	}

	public String getFfmpegUnix() {
		return ffmpegUnix;
	}

	public void setFfmpegUnix(String ffmpegUnix) {
		this.ffmpegUnix = ffmpegUnix;
	}

	public String getFfmpegLiveStream() {
		return ffmpegLiveStream;
	}

	public void setFfmpegLiveStream(String ffmpegLiveStream) {
		this.ffmpegLiveStream = ffmpegLiveStream;
	}

	public String getFfmpegPicPath() {
		return ffmpegPicPath;
	}

	public void setFfmpegPicPath(String ffmpegPicPath) {
		this.ffmpegPicPath = ffmpegPicPath;
	}

	public int getTaskReconnectInterval() {
		return taskReconnectInterval;
	}

	public void setTaskReconnectInterval(int taskReconnectInterval) {
		this.taskReconnectInterval = taskReconnectInterval;
	}

	public int getTaRkreconnectMaxTimes() {
		return taRkreconnectMaxTimes;
	}

	public void setTaRkreconnectMaxTimes(int taRkreconnectMaxTimes) {
		this.taRkreconnectMaxTimes = taRkreconnectMaxTimes;
	}

	public int getThreadCorePoolSize() {
		return threadCorePoolSize;
	}

	public void setThreadCorePoolSize(int threadCorePoolSize) {
		this.threadCorePoolSize = threadCorePoolSize;
	}

	public int getThreadMaximumPoolSize() {
		return threadMaximumPoolSize;
	}

	public void setThreadMaximumPoolSize(int threadMaximumPoolSize) {
		this.threadMaximumPoolSize = threadMaximumPoolSize;
	}

	public int getThreadKeepAliveTime() {
		return threadKeepAliveTime;
	}

	public void setThreadKeepAliveTime(int threadKeepAliveTime) {
		this.threadKeepAliveTime = threadKeepAliveTime;
	}

	public int getRtmpPort() {
		return rtmpPort;
	}

	public void setRtmpPort(int rtmpPort) {
		this.rtmpPort = rtmpPort;
	}

	public boolean isEnableHttpFlv() {
		return enableHttpFlv;
	}

	public void setEnableHttpFlv(boolean enableHttpFlv) {
		this.enableHttpFlv = enableHttpFlv;
	}

	public int getHttpFlvPort() {
		return httpFlvPort;
	}

	public void setHttpFlvPort(int httpFlvPort) {
		this.httpFlvPort = httpFlvPort;
	}

	public int getRtspPort() {
		return rtspPort;
	}

	public void setRtspPort(int rtspPort) {
		this.rtspPort = rtspPort;
	}

	public int getWssPort() {
		return wssPort;
	}

	public void setWssPort(int wssPort) {
		this.wssPort = wssPort;
	}

	public boolean isSaveFlvFile() {
		return saveFlvFile;
	}

	public void setSaveFlvFile(boolean saveFlvFile) {
		this.saveFlvFile = saveFlvFile;
	}

	public String getSaveFlVFilePath() {
		return saveFlVFilePath;
	}

	public void setSaveFlVFilePath(String saveFlVFilePath) {
		this.saveFlVFilePath = saveFlVFilePath;
	}

	public int getHandlerThreadPoolSize() {
		return handlerThreadPoolSize;
	}

	public void setHandlerThreadPoolSize(int handlerThreadPoolSize) {
		this.handlerThreadPoolSize = handlerThreadPoolSize;
	}

	public Double getSaveDataTimeInterval() {
		return saveDataTimeInterval;
	}

	public void setSaveDataTimeInterval(Double saveDataTimeInterval) {
		this.saveDataTimeInterval = saveDataTimeInterval;
	}

	public Double getDeleteDataTimeInterval() {
		return deleteDataTimeInterval;
	}

	public void setDeleteDataTimeInterval(Double deleteDataTimeInterval) {
		this.deleteDataTimeInterval = deleteDataTimeInterval;
	}

	public Double getDeleteDataHour() {
		return deleteDataHour;
	}

	public void setDeleteDataHour(Double deleteDataHour) {
		this.deleteDataHour = deleteDataHour;
	}

	public String getRedisAddr() {
		return redisAddr;
	}

	public void setRedisAddr(String redisAddr) {
		this.redisAddr = redisAddr;
	}

	public String getRedisAuth() {
		return redisAuth;
	}

	public void setRedisAuth(String redisAuth) {
		this.redisAuth = redisAuth;
	}

	public String getIotLinkip() {
		return iotLinkip;
	}

	public void setIotLinkip(String iotLinkip) {
		this.iotLinkip = iotLinkip;
	}

	public String getIotLinkport() {
		return iotLinkport;
	}

	public void setIotLinkport(String iotLinkport) {
		this.iotLinkport = iotLinkport;
	}

	public String getIotLink() {
		return iotLink;
	}

	public void setIotLink(String iotLink) {
		this.iotLink = iotLink;
	}

	public boolean isHaproxy() {
		return isHaproxy;
	}

	public void setHaproxy(boolean isHaproxy) {
		this.isHaproxy = isHaproxy;
	}

	public boolean isSaveMp4File() {
		return saveMp4File;
	}

	public void setSaveMp4File(boolean saveMp4File) {
		this.saveMp4File = saveMp4File;
	}

	public String getSaveMp4FilePath() {
		return saveMp4FilePath;
	}

	public void setSaveMp4FilePath(String saveMp4FilePath) {
		this.saveMp4FilePath = saveMp4FilePath;
	}

	public static MyLiveConfig getInstance() {
		return SpringUtils.getBean(MyLiveConfig.class);
	}
}
