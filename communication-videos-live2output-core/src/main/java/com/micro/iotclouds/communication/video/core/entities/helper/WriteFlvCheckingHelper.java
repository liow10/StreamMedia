/**
 * 
 */
package com.micro.iotclouds.communication.video.core.entities.helper;

import com.micro.iotclouds.communication.video.core.domain.Constants;
import com.micro.iotclouds.communication.video.core.messages.RtmpMediaMessage;
import com.micro.iotclouds.communication.video.core.messages.VideoMessage;

/**
 * 写视频辅助类
 * 
 * @author Joe
 *
 */
public class WriteFlvCheckingHelper {

	/**
	 * 检查当前帧是否视频起始帧，若是则可以分割
	 * 
	 * @param msg
	 * @return
	 */
	public static boolean checkIsVideoKeyFrame(RtmpMediaMessage msg) {
		int tagType = msg.getMsgType();
		if (Constants.MSG_TYPE_VIDEO_MESSAGE != tagType) {
			return false;
		}
		if (msg instanceof VideoMessage) {
			VideoMessage vm = (VideoMessage) msg;
			return vm.isVideoFrameData(msg.raw());
		}
		return false;
	}
}
