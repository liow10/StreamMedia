package com.micro.iotclouds.communication.video.core.entities.helper;
import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
 
/**
 * @author hilbert.xu
 */
public class Convert {
 
    private static final Logger log = LoggerFactory.getLogger(Convert.class);
 
    private static ByteArrayOutputStream convert2OutputStream(InputStream inputStream) {
        Frame audioSamples = null;
        ByteArrayOutputStream swapStream = null;
        // 音频录制（输出地址，音频通道）
        FFmpegFrameRecorder recorder = null;
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        // 开启抓取器
        if (start(grabber)) {
            swapStream = new ByteArrayOutputStream();
            recorder = new FFmpegFrameRecorder(swapStream, 1);
            recorder.setAudioOption("crf", "0");
            recorder.setAudioBitrate(16);
            recorder.setAudioChannels(1);
            recorder.setSampleRate(16000);
            recorder.setAudioQuality(0);
            recorder.setFormat("wav"); // 具体转化音视频类型
            recorder.setAudioOption("aq", "10");
            // 开启录制器
            if (start(recorder)) {
                try {
                    // 抓取音频
                    while ((audioSamples = grabber.grab()) != null) {
                        recorder.setTimestamp(grabber.getTimestamp());
                        recorder.record(audioSamples);
                    }
                } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
                    log.error("抓取失败");
                } catch (Exception e) {
                    log.error("录制失败");
                }
                stop(grabber);
                stop(recorder);
            }
        }
        return swapStream;
    }
 
    public static String convert2String(InputStream inputStream) {
        ByteArrayOutputStream outputStream = convert2OutputStream(inputStream);
        return outputStream.toString();
    }
 
    public static byte[] convert2ByteArray(InputStream inputStream) {
        ByteArrayOutputStream outputStream = convert2OutputStream(inputStream);
        return outputStream.toByteArray();
    }
 
    private static boolean start(FrameGrabber grabber) {
        try {
            grabber.start();
            return true;
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e2) {
            try {
                log.error("首次打开抓取器失败，准备重启抓取器...");
                grabber.restart();
                return true;
            } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
                try {
                    log.error("重启抓取器失败，正在关闭抓取器...");
                    grabber.stop();
                } catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
                    log.error("停止抓取器失败！");
                }
            }
 
        }
        return false;
    }
 
    private static boolean start(FrameRecorder recorder) {
        try {
            recorder.start();
            return true;
        } catch (Exception e2) {
            try {
                log.error("首次打开录制器失败！准备重启录制器...");
                recorder.stop();
                recorder.start();
                return true;
            } catch (Exception e) {
                try {
                    log.error("重启录制器失败！正在停止录制器...");
                    recorder.stop();
                } catch (Exception e1) {
                    log.error("关闭录制器失败！");
                }
            }
        }
        return false;
    }
 
    private static boolean stop(FrameGrabber grabber) {
        try {
            grabber.flush();
            grabber.stop();
            return true;
        } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
            return false;
        } finally {
            try {
                grabber.stop();
            } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
                log.error("关闭抓取器失败");
            }
        }
    }
 
    private static boolean stop(FrameRecorder recorder) {
        try {
            recorder.stop();
            recorder.release();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                recorder.stop();
            } catch (Exception e) {
 
            }
        }
    }
}