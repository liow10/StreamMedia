//package com.mkst.iotclouds.communication.protocol.newrtsp.handler;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.socket.DatagramPacket;
//
//public class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
//		System.out.println(msg.content().readableBytes());
//	}
//
//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		super.channelActive(ctx);
//		System.out.println("udp handler active");
//	}
//
//	@Override
//	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		super.channelInactive(ctx);
//		System.out.println("udp handler inactive");
//	}
//}
