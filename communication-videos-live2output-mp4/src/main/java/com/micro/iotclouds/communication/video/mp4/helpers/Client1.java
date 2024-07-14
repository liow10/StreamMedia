package com.micro.iotclouds.communication.video.mp4.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client1 implements Runnable {

	InputStream objInputStream;

	/**
	 * Socket客户端
	 */
	public static void main(String[] args) throws Exception {
		try {
			int count = 0;
			while (true) {
// "GET /ipcam/jpeg.cgi HTTP/1.1\r\n\r\nAuthorization: Basic YWRtaW46OTk5OQ====\r\n\r\n"
// 创建Socket对象
				Socket socket = new Socket("192.168.0.80", 8080);
// 根据输入输出流和服务端连接
				OutputStream outputStream = socket.getOutputStream();// 获取一个输出流，向服务端发送信息
// "GET /ipcam/jpeg.cgi HTTP/1.1\r\nAuthorization: Basic YWRtaW46OTk5OQ====\r\n\r\n"
				outputStream.write(
						"GET /ipcam/avc.cgi HTTP/1.1\r\nAuthorization: Basic YWRtaW46OTk5OQ====\r\n\r\n".getBytes());
				outputStream.flush();
				socket.shutdownOutput();// 关闭输出流
				InputStream inputStream = socket.getInputStream();// 获取一个输入流，接收服务端的信息
				String outputFile = "rtmp://localhost:1935/live/test2";
				Player.frameRecord(inputStream, outputFile, 1);
				count++;
				System.out.println("================================" + count + "次数");
				socket.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Client1(InputStream objInputStream) {
		this.objInputStream = objInputStream;
	}

	@Override
	public void run() {
		String outputFile = "rtmp://localhost:1935/live/test2";
		try {
			Player.frameRecord(objInputStream, outputFile, 1);
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void runVideo(byte[] dst) {
		String outputFile = "rtmp://localhost:1935/live/test2";
//		try {
//			//Player.frameRecord(dst, outputFile, 1);
//		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}