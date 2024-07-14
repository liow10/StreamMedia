package com.micro.iotclouds.communication.video.core.entities.helper;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedMultiThreadHelper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream();
		try {
			pis.connect(pos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Producer p = new Producer(pos);
		Consumer c = new Consumer(pis);
		
		Thread tp1 = new Thread(p, "生产者1号");
		
		Thread tc1 = new Thread(c, "消费者1号");
		
		tc1.start();
		
		tp1.start();
		
	}

	private static class Producer implements Runnable {
		private PipedOutputStream pos;

		public Producer(PipedOutputStream pos) {
			this.pos = pos;
		}

		@Override
		public void run() {
			try {
				for (int i = 1; i <= 2; i++) {
					pos.write(("threadName="+Thread.currentThread().getName()+", messageId=" + i + "\n").getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	private static class Consumer implements Runnable {
		private PipedInputStream pis;

		public Consumer(PipedInputStream pis) {
			this.pis = pis;
		}
		
		@Override
		public void run() {
			int len = -1;
			byte[] buffer = new byte[1024];
			try {
				//read(buffer, 0, buffer.length)函数作用有两个：
				//(1)若有buffer.length个数据可读，则返回buffer.length个数据；
				//   否则读取当前可读的所有数据，个数小于buffer.length;
				//(2)若没有数据可读，则让读进程等待(见read()函数)
				while ((len = pis.read(buffer)) != -1) {
					System.out.println(new String(buffer, 0, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}