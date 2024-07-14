//package com.micro.iotclouds.modules.rtmp.server.sip.main;
//import java.text.ParseException;
//import java.util.TooManyListenersException;
//
//import javax.sip.InvalidArgumentException;
//import javax.sip.ObjectInUseException;
//import javax.sip.PeerUnavailableException;
//import javax.sip.SipException;
//import javax.sip.TransportNotSupportedException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class SIPMain {
//	
//	protected Logger logger = LoggerFactory.getLogger(SIPMain.class);
//
//	public void run() {
//		//用户名,IP地址,端口
//		try {
//			int port = 5060;
//			SipLayer sipLayer = new SipLayer("admin" , "172.24.20.26" , port);  //本地
//			//SipLayer sipLayer = new SipLayer("admin","xx.xx.xx.xx",port); //阿里云上的IP  VECS01532
//			//sipLayer.setMessageProcessor(new MessageProcessorImpl());
//			System.out.println("服务启动完毕, 已经在"+port+"端口监听消息\n\n");
//		} catch (PeerUnavailableException e) {
//			e.printStackTrace();
//			//logger.error(ExceptionUtils.getFullStackTrace(e));
//		} catch (TransportNotSupportedException e) {
//			e.printStackTrace();
//			//logger.error(ExceptionUtils.getFullStackTrace(e));
//		} catch (ObjectInUseException e) {
//			e.printStackTrace();
//			//logger.error(ExceptionUtils.getFullStackTrace(e));
//		} catch (InvalidArgumentException e) {
//			e.printStackTrace();
//			//logger.error(ExceptionUtils.getFullStackTrace(e));
//		} catch (TooManyListenersException e) {
//			e.printStackTrace();
//			//logger.error(ExceptionUtils.getFullStackTrace(e));
//		} 
//	}
//	
//	
//	/**
//	 * 这个方法暂时用不上，目前系统没有需要主动发送消息给SIP终端设备的业务场景
//	 * @throws InvalidArgumentException
//	 * @throws TooManyListenersException
//	 * @throws ParseException
//	 * @throws SipException
//	 */
//	public void sendMsg() throws InvalidArgumentException, TooManyListenersException, ParseException, SipException{
//		SipLayer sipLayer = new SipLayer("admin","127.0.0.1",5060);
//		sipLayer.sendMessage(sipLayer.getUsername(), sipLayer.getHost(), "test message");
//	}
//
//}