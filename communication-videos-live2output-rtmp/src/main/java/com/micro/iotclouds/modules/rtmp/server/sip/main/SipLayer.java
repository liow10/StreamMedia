//package com.micro.iotclouds.modules.rtmp.server.sip.main;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Properties;
//import java.util.TooManyListenersException;
//
//import javax.sip.DialogTerminatedEvent;
//import javax.sip.IOExceptionEvent;
//import javax.sip.InvalidArgumentException;
//import javax.sip.ListeningPoint;
//import javax.sip.ObjectInUseException;
//import javax.sip.PeerUnavailableException;
//import javax.sip.RequestEvent;
//import javax.sip.ResponseEvent;
//import javax.sip.SipException;
//import javax.sip.SipFactory;
//import javax.sip.SipListener;
//import javax.sip.SipProvider;
//import javax.sip.SipStack;
//import javax.sip.TimeoutEvent;
//import javax.sip.TransactionTerminatedEvent;
//import javax.sip.TransportNotSupportedException;
//import javax.sip.address.Address;
//import javax.sip.address.AddressFactory;
//import javax.sip.address.SipURI;
//import javax.sip.header.CSeqHeader;
//import javax.sip.header.CallIdHeader;
//import javax.sip.header.ContactHeader;
//import javax.sip.header.ContentTypeHeader;
//import javax.sip.header.FromHeader;
//import javax.sip.header.HeaderFactory;
//import javax.sip.header.MaxForwardsHeader;
//import javax.sip.header.ToHeader;
//import javax.sip.header.ViaHeader;
//import javax.sip.message.MessageFactory;
//import javax.sip.message.Request;
//import javax.sip.message.Response;
//
//
//public class SipLayer implements SipListener {
//	
//	private MessageProcessor messageProcessor;
//
//	private String username;
//
//	private SipStack sipStack;
//
//	private SipFactory sipFactory;
//
//	private AddressFactory addressFactory;
//
//	private HeaderFactory headerFactory;
//
//	private MessageFactory messageFactory;
//
//	private SipProvider sipProvider;
//
//	/** Here we initialize the SIP stack. */
//	@SuppressWarnings("deprecation")
//	public SipLayer(String username, String ip, int port) throws PeerUnavailableException,
//			TransportNotSupportedException, InvalidArgumentException, ObjectInUseException, TooManyListenersException {
//		setUsername(username);
//		sipFactory = SipFactory.getInstance();
//		sipFactory.setPathName("gov.nist");
//		Properties properties = new Properties();
//		properties.setProperty("javax.sip.STACK_NAME", "cameraReg");
//		properties.setProperty("javax.sip.IP_ADDRESS", ip);
//
//		/**
//		 * sip_server_log.log 和 sip_debug_log.log
//		 * 	public static final int TRACE_NONE = 0;
//			public static final int TRACE_MESSAGES = 16;
//			public static final int TRACE_EXCEPTION = 17;
//			public static final int TRACE_DEBUG = 32;
//		 */
//		properties.setProperty("gov.nist.javax.sip.TRACE_LEVEL", "16"); 
//		properties.setProperty("gov.nist.javax.sip.SERVER_LOG", "sip_server_log");
//		properties.setProperty("gov.nist.javax.sip.DEBUG_LOG", "sip_debug_log");
//
//		sipStack = sipFactory.createSipStack(properties);
//		headerFactory = sipFactory.createHeaderFactory();
//		addressFactory = sipFactory.createAddressFactory();
//		messageFactory = sipFactory.createMessageFactory();
//
//		ListeningPoint tcp = sipStack.createListeningPoint(port, "tcp");
//		ListeningPoint udp = sipStack.createListeningPoint(port, "udp");
//
//		sipProvider = sipStack.createSipProvider(tcp);
//		sipProvider.addSipListener(this);
//		sipProvider = sipStack.createSipProvider(udp);
//		sipProvider.addSipListener(this);
//	}
//
//	/**
//	 * This method uses the SIP stack to send a message. 第一个参数：用户名 第二个参数：IP地址
//	 * 第三个参数：消息内容
//	 */
//	public void sendMessage(String username, String address, String message)
//			throws ParseException, InvalidArgumentException, SipException {
//
//		SipURI from = addressFactory.createSipURI(getUsername(), getHost() + ":" + getPort());
//		Address fromNameAddress = addressFactory.createAddress(from);
//		fromNameAddress.setDisplayName(getUsername());
//		FromHeader fromHeader = headerFactory.createFromHeader(fromNameAddress, "cameraReg1.0");
//
//		SipURI toAddress = addressFactory.createSipURI(username, address);
//		Address toNameAddress = addressFactory.createAddress(toAddress);
//		toNameAddress.setDisplayName(username);
//		ToHeader toHeader = headerFactory.createToHeader(toNameAddress, null);
//
//		SipURI requestURI = addressFactory.createSipURI(username, address);
//		requestURI.setTransportParam("udp");
//
//		ArrayList<ViaHeader> viaHeaders = new ArrayList<ViaHeader>();
//		ViaHeader viaHeader = headerFactory.createViaHeader(getHost(), getPort(), "udp", "branch1");
//		viaHeaders.add(viaHeader);
//
//		CallIdHeader callIdHeader = sipProvider.getNewCallId();
//
//		@SuppressWarnings("deprecation")
//		CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1, Request.MESSAGE);
//
//		MaxForwardsHeader maxForwards = headerFactory.createMaxForwardsHeader(70);
//
//		Request request = messageFactory.createRequest(requestURI, Request.MESSAGE, callIdHeader, cSeqHeader,
//				fromHeader, toHeader, viaHeaders, maxForwards);
//
//		SipURI contactURI = addressFactory.createSipURI(getUsername(), getHost());
//		contactURI.setPort(getPort());
//		Address contactAddress = addressFactory.createAddress(contactURI);
//		contactAddress.setDisplayName(getUsername());
//		ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
//		request.addHeader(contactHeader);
//
//		ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("text", "plain");
//		request.setContent(message, contentTypeHeader);
//
//		sipProvider.sendRequest(request);
//	}
//
//	/** This method is called by the SIP stack when a response arrives. */
//	public void processResponse(ResponseEvent evt) {
//		Response response = evt.getResponse();
//		int status = response.getStatusCode();
//
//		if ((status >= 200) && (status < 300)) { // Success!
//			messageProcessor.processInfo("--Sent");
//			return;
//		}
//
//		messageProcessor.processError("Previous message not sent: " + status);
//	}
//
//	/**
//	 * SIP服务端接收消息的方法
//	 * Content 里面是GBK编码
//	 * This method is called by the SIP stack when a new request arrives.
//	 */
//	public void processRequest(RequestEvent evt) {
//		Request req = evt.getRequest();
//		messageProcessor.processMessage(req,messageFactory,sipProvider);
//	}
//	
//
//	/**
//	 * This method is called by the SIP stack when there's no answer to a
//	 * message. Note that this is treated differently from an error message.
//	 */
//	public void processTimeout(TimeoutEvent evt) {
//		messageProcessor.processError("Previous message not sent: " + "timeout");
//	}
//
//	/**
//	 * This method is called by the SIP stack when there's an asynchronous
//	 * message transmission error.
//	 */
//	public void processIOException(IOExceptionEvent evt) {
//		messageProcessor.processError("Previous message not sent: " + "I/O Exception");
//	}
//
//	/**
//	 * This method is called by the SIP stack when a dialog (session) ends.
//	 */
//	public void processDialogTerminated(DialogTerminatedEvent evt) {
//	}
//
//	/**
//	 * This method is called by the SIP stack when a transaction ends.
//	 */
//	public void processTransactionTerminated(TransactionTerminatedEvent evt) {
//	}
//
//	@SuppressWarnings("deprecation")
//	public String getHost() {
//		String host = sipStack.getIPAddress();
//		return host;
//	}
//
//	@SuppressWarnings("deprecation")
//	public int getPort() {
//		int port = sipProvider.getListeningPoint().getPort();
//		return port;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String newUsername) {
//		username = newUsername;
//	}
//
//	public MessageProcessor getMessageProcessor() {
//		return messageProcessor;
//	}
//
//	public void setMessageProcessor(MessageProcessor newMessageProcessor) {
//		messageProcessor = newMessageProcessor;
//	}
//
//}