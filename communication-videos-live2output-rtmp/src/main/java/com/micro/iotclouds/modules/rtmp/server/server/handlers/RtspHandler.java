//package com.micro.iotclouds.modules.rtmp.server.server.handlers;
//
//import com.micro.iotclouds.modules.rtmp.server.amf.Amf0Object;
//import com.micro.iotclouds.modules.rtmp.server.server.entities.Role;
//import com.micro.iotclouds.modules.rtmp.server.server.entities.Stream;
//import com.micro.iotclouds.modules.rtmp.server.server.entities.StreamName;
//import com.micro.iotclouds.modules.rtmp.server.server.manager.StreamManager;
//import com.micro.iotclouds.modules.rtmp.server.server.rtmp.Constants;
//import com.micro.iotclouds.modules.rtmp.server.server.rtmp.messages.*;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.DefaultHttpRequest;
//import io.netty.handler.codec.http.HttpContent;
//import io.netty.handler.codec.http.HttpMethod;
//import lombok.extern.slf4j.Slf4j;
//import org.bytedeco.javacpp.avcodec;
//import org.bytedeco.javacv.*;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import static org.bytedeco.javacpp.avutil.AV_PIX_FMT_YUV420P;
//
//@Slf4j
//public class RtspHandler extends ChannelInboundHandlerAdapter {
//
//    int ackWindowSize;
//    int lastSentbackSize;
//    int bytesReceived;
//
//    Role role;
//    boolean normalShutdown;
//
//    private StreamName streamName;
//
//    private StreamManager streamManager;
//
//    public RtspHandler(StreamManager manager) {
//        this.streamManager = manager;
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        if (!normalShutdown && role == Role.Publisher) {
//            Stream stream = streamManager.getStream(streamName);
//            if (stream != null) {
//                stream.sendEofToAllSubscriberAndClose();
//            } else {
//                log.error("channel inactive but stream:{} is null", streamName);
//            }
//        }
//    }
//
//    /**
//     * RTMP接收消息类
//     * @param ctx 头文件
//     * @param msg 消息内容
//     * @throws Exception
//     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//
////        PipedInputStream pipedInputStream=new PipedInputStream();
//        PipedOutputStream pipedOutputStream=new PipedOutputStream();
////        ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
//        FFmpegFrameGrabber grabber = null;
////        CustomFFmpegFrameRecorder recorder = null;
//        try {
////            FrameGrabber grabber = new OpenCVFrameGrabber(0);
////
////            grabber.setImageHeight(500);
////            grabber.setImageWidth(500);
////            grabber.start();
//
//            if(msg instanceof DefaultHttpRequest){
//                DefaultHttpRequest req = (DefaultHttpRequest)msg;
//                HttpMethod method = req.method();
//                String[] app = req.getUri().split("/live/");
//                String uri = app[1];
//                StreamName sn = new StreamName("live", uri);
//
////                pipedInputStream.connect(pipedOutputStream);
//                synchronized (this){
//                    Stream stream = streamManager.getStream(sn);
//                    RtspToRtspHandler handler = new RtspToRtspHandler();
//
//                    List<RtmpMediaMessage> content = stream.getContent();
//                    // run方法具体重写
//                    for (RtmpMediaMessage msg1 : content) {
//                    pipedOutputStream.write(msg1.raw());
////                    bOutput.write(msg1.raw());
//                        pipedOutputStream.flush();
//                        handler.playH264(pipedOutputStream);
//                    }
//                }
////                ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
////                cachedThreadPool.execute(new Runnable() {
////
////                    @Override
////                    public void run() {
////
////                    }
////                });
////                cachedThreadPool.shutdown();
//
//
//
////                FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputStream, 1280, 720);
//
////                PipedInputStream pis = new PipedInputStream(outputStream);;
////                grabber = new FFmpegFrameGrabber(pis,0);
//////            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("rtsp://admin:hik12345@192.168.1.4:554/h264/ch1/main/av_stream", 1280, 720);
////
////                grabber.start();
////                recorder = new CustomFFmpegFrameRecorder(address,1280,720,0);
////                recorder.setInterleaved(true);
////                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
////                recorder.setFormat("flv");
////                recorder.setFrameRate(25);
////                recorder.start(grabber.getFormatContext());
//
////                String methodName = method.name();
//                /** 以下就是具体消息的处理, 需要开发者自己实现 */
////                if(methodName.equalsIgnoreCase("OPTIONS") ||
////                        methodName.equalsIgnoreCase("DESCRIBE")){
////                }else{
////
////                }
//            }else if(msg instanceof HttpContent){
//                HttpContent content = (HttpContent)msg;
//                if(content.content().isReadable()) {
//                    /** 此时, 才表示HttpContent是有内容的, 否则,它是空的, 不需要处理 */
//                }
//            }
//
//
//
////            RtmpMediaMessage rtmpMediaMessage = (RtmpMediaMessage)msg;
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void handleUserControl(ChannelHandlerContext ctx, UserControlMessageEvent msg) {
////		boolean isBufferLength = msg.isBufferLength();
////		if (isBufferLength) {
////			if (role == Role.Subscriber) {
////				startPlay(ctx, streamManager.getStream(streamName));
////			}
////		}
//
//    }
//
//    private void handleMedia(ChannelHandlerContext ctx, RtmpMediaMessage msg) {
//        Stream stream = streamManager.getStream(streamName);
//        if (stream == null) {
//            log.error("stream:{} not exist!", streamName);
//            return;
//        }
//        stream.addContent(msg);
//    }
//
//    private void handleDataMessage(ChannelHandlerContext ctx, RtmpDataMessage msg) {
//
//        String name = (String) msg.getData().get(0);
//        if ("@setDataFrame".equals(name)) {
//            // save on metadata
//            Map<String, Object> properties = (Map<String, Object>) msg.getData().get(2);
//            properties.remove("filesize");
//            Stream stream = streamManager.getStream(streamName);
//            stream.setMetadata(properties);
//        }
//
//    }
//
//    private void handleCommand(ChannelHandlerContext ctx, RtmpCommandMessage msg) {
//        List<Object> command = msg.getCommand();
//        String commandName = (String) command.get(0);
//        switch (commandName) {
//            case "connect":
//                //创建连接
//                handleConnect(ctx, msg);
//                break;
//            case "createStream":
//                //创建流
//                handleCreateStream(ctx, msg);
//                break;
//            case "publish":
//                //推送
//                handlePublish(ctx, msg);
//                break;
//            case "play":
//                //开始播放
//                handlePlay(ctx, msg);
//                break;
//            case "deleteStream":
//            case "closeStream":
//                //关闭流
//                handleCloseStream(ctx, msg);
//                break;
//            default:
//                break;
//        }
//
//    }
//
//    private void handleCloseStream(ChannelHandlerContext ctx, RtmpCommandMessage msg) {
//        if (role == Role.Subscriber) {
//            log.info("one subscriber delete stream.do nothing");
//            return;
//        }
//        // send back 'NetStream.Unpublish.Success' to publisher
//        RtmpCommandMessage onStatus = onStatus("status", "NetStream.Unpublish.Success", "Stop publishing");
//        ctx.write(onStatus);
//        // send User Control Message Stream EOF (1) to all subscriber
//        // and we close all publisher and subscribers
//        Stream stream = streamManager.getStream(streamName);
//        if (stream == null) {
//            log.error("can't find stream:{} in stream manager", streamName);
//        } else {
//            stream.sendEofToAllSubscriberAndClose();
//            streamManager.remove(streamName);
//            normalShutdown = true;
//            ctx.close();
//
//        }
//    }
//
//    private void handlePlay(ChannelHandlerContext ctx, RtmpCommandMessage msg) {
//        role = Role.Subscriber;
//
//        String name = (String) msg.getCommand().get(3);
//
//        //使用token命名文件
//        streamName.setName(name);
//
//        Stream stream = streamManager.getStream(streamName);
//        if (stream == null) {
//            // NetStream.Play.StreamNotFound
//            RtmpCommandMessage onStatus = onStatus("error", "NetStream.Play.StreamNotFound", "No Such Stream");
//
//            ctx.writeAndFlush(onStatus);
//
//            normalShutdown = true;
//            ctx.channel().close();
//
//        }else {
//            startPlay(ctx, stream);
//        }
//
//        // real play happens when setBuffer
//
//    }
//
//    private void startPlay(ChannelHandlerContext ctx, Stream stream) {
//        if(stream==null) {
//            return;
//        }
//        ctx.writeAndFlush(UserControlMessageEvent.streamBegin(Constants.DEFAULT_STREAM_ID));
//
//        RtmpCommandMessage onStatus = onStatus("status", "NetStream.Play.Start", "Start live");
//
//        ctx.writeAndFlush(onStatus);
//
//        List<Object> args = new ArrayList<>();
//        args.add("|RtmpSampleAccess");
//        args.add(true);
//        args.add(true);
//        RtmpCommandMessage rtmpSampleAccess = new RtmpCommandMessage(args);
//
//        ctx.writeAndFlush(rtmpSampleAccess);
//
//        List<Object> metadata = new ArrayList<>();
//        metadata.add("onMetaData");
//        metadata.add(stream.getMetadata());
//        RtmpDataMessage msgMetadata = new RtmpDataMessage(metadata);
//
//        ctx.writeAndFlush(msgMetadata);
//
////		AudioMessage emptt = new AudioMessage(Unpooled.EMPTY_BUFFER);
////		ctx.writeAndFlush(emptt);
//
//        stream.addSubscriber(ctx.channel());
//    }
//
//    private void handlePublish(ChannelHandlerContext ctx, RtmpCommandMessage msg) {
//        log.info("publish :{}", msg);
//        role = Role.Publisher;
//        String name = (String) msg.getCommand().get(3);
//        streamName.setName(name);
//
//        String streamType = (String) msg.getCommand().get(4);
//        if (!"live".equals(streamType)) {
//            log.error("unsupport stream type :{}", streamType);
//            ctx.channel().disconnect();
//        }
//
//        createStream(ctx);
//        // reply a onStatus
//        RtmpCommandMessage onStatus = onStatus("status", "NetStream.Publish.Start", "Start publishing");
//
//        ctx.writeAndFlush(onStatus);
//
//    }
//
//    private void createStream(ChannelHandlerContext ctx) {
//        Stream s = new Stream(streamName);
//        s.setPublisher(ctx.channel());
//        streamManager.newStream(streamName, s);
//    }
//
//    private void handleCreateStream(ChannelHandlerContext ctx, RtmpCommandMessage msg) {
//
//        log.info("create stream received : {}", msg);
//
//        List<Object> result = new ArrayList<Object>();
//        result.add("_result");
//        result.add(msg.getCommand().get(1));// transaction id
//        result.add(null);// properties
//        result.add(Constants.DEFAULT_STREAM_ID);// stream id
//
//        RtmpCommandMessage response = new RtmpCommandMessage(result);
//
//        ctx.writeAndFlush(response);
//
//    }
//
//    private void handleConnect(ChannelHandlerContext ctx, RtmpCommandMessage msg) {
//
//        // client send connect
//        // server reply windows ack size and set peer bandwidth
//
//        log.info("client connect {} ", msg);
//
//        String app = (String) ((Map) msg.getCommand().get(2)).get("app");
//        Integer clientRequestEncode = (Integer) ((Map) msg.getCommand().get(2)).get("objectEncoding");
//        if(clientRequestEncode !=null && clientRequestEncode.intValue()==3) {
//            log.error("client :{} request AMF3 encoding but server currently doesn't support",ctx);
//            ctx.close();
//            return ;
//        }
//        streamName = new StreamName(app, null);
//
//        int ackSize = 5000000;
//        WindowAcknowledgementSize was = new WindowAcknowledgementSize(ackSize);
//
//        SetPeerBandwidth spb = new SetPeerBandwidth(ackSize, Constants.SET_PEER_BANDWIDTH_TYPE_SOFT);
//
//        SetChunkSize setChunkSize = new SetChunkSize(5000);
//
//        ctx.writeAndFlush(was);
//        ctx.writeAndFlush(spb);
//        ctx.writeAndFlush(setChunkSize);
//
//        List<Object> result = new ArrayList<Object>();
//        result.add("_result");
//        result.add(msg.getCommand().get(1));// transaction id
//        result.add(new Amf0Object().addProperty("fmsVer", "FMS/3,0,1,123").addProperty("capabilities", 31));
//        result.add(new Amf0Object().addProperty("level", "status").addProperty("code", "NetConnection.Connect.Success")
//                .addProperty("description", "Connection succeeded").addProperty("objectEncoding", 0));
//
//        RtmpCommandMessage response = new RtmpCommandMessage(result);
//
//        ctx.writeAndFlush(response);
//
//    }
//
//    private void maySendAck(ChannelHandlerContext ctx, RtmpMessage msg) {
//
//        // we need ack when receive bytes greater than ack window
//        // this is not an accurate implement
//        int receiveBytes = msg.getInboundBodyLength() + msg.getInboundHeaderLength();
//        bytesReceived += receiveBytes;
//
//        if (ackWindowSize <= 0) {
//            return;
//        }
//        // bytes received may over flow at ~2GB
//        // we need reset here
//        if (bytesReceived > 0X70000000) {
//            log.warn("reset bytesReceived in case of overflow.");
//            ctx.writeAndFlush(new Acknowledgement(bytesReceived));
//            bytesReceived = 0;
//            lastSentbackSize = 0;
//            return;
//        }
//
//        if (bytesReceived - lastSentbackSize >= ackWindowSize) {
//            // write an ack to client
//            lastSentbackSize = bytesReceived;
//            ctx.writeAndFlush(new Acknowledgement(lastSentbackSize));
//        }
//    }
//
//    public RtmpCommandMessage onStatus(String level, String code, String description) {
//        List<Object> result = new ArrayList<>();
//        result.add("onStatus");
//        result.add(0);// always 0
//        result.add(null);// properties
//        result.add(new Amf0Object().addProperty("level", level).addProperty("code", code).addProperty("description",
//                description));// stream id
//
//        RtmpCommandMessage response = new RtmpCommandMessage(result);
//        return response;
//    }
//}
