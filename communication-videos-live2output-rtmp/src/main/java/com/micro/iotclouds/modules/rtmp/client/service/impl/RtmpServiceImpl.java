///*
// * Copyright 2019 Pnoker. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//
//package com.micro.iotclouds.modules.rtmp.client.service.impl;
//
//import static java.lang.System.getProperty;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Service;
//
//import com.google.common.collect.Lists;
//import com.micro.iotclouds.modules.protocols.rtmp.domain.CommonRtmp;
//import com.micro.iotclouds.modules.protocols.rtmp.mapper.CommonRtmpMapper;
//import com.micro.iotclouds.modules.rtmp.client.domain.CmdTask;
//import com.micro.iotclouds.modules.rtmp.client.domain.Global;
//import com.micro.iotclouds.modules.rtmp.client.domain.Response;
//import com.micro.iotclouds.modules.rtmp.client.domain.Rtmp;
//import com.micro.iotclouds.modules.rtmp.client.service.RtmpService;
//import com.micro.iotclouds.modules.rtmp.client.utils.Tools;
//import com.micro.iotclouds.modules.rtmp.common.cfg.MyLiveConfig;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * <p>Copyright(c) 2019. Pnoker All Rights Reserved.
// * <p>@Author    : Pnoker
// * <p>Email      : pnokers@gmail.com
// * <p>Description:
// */
//@Slf4j
//@Service
////@ConfigurationProperties(prefix = "ffmpeg")
//public class RtmpServiceImpl implements RtmpService {
//
//	@Autowired
//	private CommonRtmpMapper commonRtmpMapper;
//
//    private String unix;
//    private String window;
//
//    private volatile int times = 1;
//
//	/*
//	 * @Autowired private RtmpDbsFeignApi rtmpDbsFeignApi;
//	 */
//
//    @Override
//    public List<Rtmp> getRtmpList() {
////        Response<List<Rtmp>> response = rtmpDbsFeignApi.list();
////        if (!response.isOk() || times == 1) {
////            times = 2;
////            response.setData(null);
////            log.error(response.getMessage());
////            return reconnect();
////        }
////        List<Rtmp> list = response.getData();
//    	CommonRtmp deviceRtmp = new CommonRtmp();
//    	List<CommonRtmp> lstCommonRtmp = commonRtmpMapper.selectDeviceRtmpList(deviceRtmp);
//    	List<Rtmp> list = Lists.newArrayList();
//    	for(CommonRtmp rtmp : lstCommonRtmp) {
//    		Rtmp objRtmp = new Rtmp();
//    		objRtmp.setAutoStart(rtmp.getAutoStart()!=null?Boolean.parseBoolean(rtmp.getAutoStart()):false);
//    		objRtmp.setCommand(rtmp.getCommand());
//    		objRtmp.setId(rtmp.getId());
//    		objRtmp.setRtmpUrl(rtmp.getRtmpUrl());
//    		objRtmp.setRtspUrl(rtmp.getLiveUrl());
//    		objRtmp.setVideoType(rtmp.getVideoType());
//    		list.add(objRtmp);
//    	}
//        return list != null ? list : new ArrayList<>();
//    }
//
//    @Override
//    public boolean createCmdTask(Rtmp rtmp) {
//        String ffmpeg = getProperty("os.name").toLowerCase().startsWith("win") ? MyLiveConfig.getInstance().getFfmpegWindow() : MyLiveConfig.getInstance().getFfmpegUnix();
//        if ("".equals(ffmpeg) || null == ffmpeg) {
//            log.error("FFmpeg path is NULL !");
//            return false;
//        }
//        if (!Tools.isFile(ffmpeg)) {
//            log.error("{} does not exist", ffmpeg);
//            return false;
//        }
//       // String live
//        String cmd = rtmp.getCommand()
//                .replace("{exe}", ffmpeg)
//                .replace("{live_url}", rtmp.getRtspUrl()!=null?rtmp.getRtspUrl():"")
//                .replace("{play_id}", rtmp.getId()+"");
//        return createCmdTask(new CmdTask(cmd,rtmp.getId() + ""));
//    }
//
//    @Override
//    public boolean stopCmdTask(String id) {
//        Global.taskMap.get(id).stop();
//        return false;
//    }
//
//    /**
//     * 创建视频转码任务
//     *
//     * @param cmdTask
//     */
//    public static boolean createCmdTask(CmdTask cmdTask) {
//        // 判断任务是否被重复提交
//        if (!Global.taskMap.containsKey(cmdTask.getId())) {
//            if (Global.taskMap.size() <= Global.MAX_TASK_SIZE) {
//                Global.taskMap.put(cmdTask.getId(), cmdTask);
//                return cmdTask.create();
//            } else {
//                log.error("超过最大任务数 {}", Global.MAX_TASK_SIZE);
//                return false;
//            }
//        } else {
//            log.error("重复任务 {}", cmdTask.getId());
//            return false;
//        }
//    }
//
//    public List<Rtmp> reconnect() {
//        // N 次重连机会
//        if (times > Global.CONNECT_MAX_TIMES) {
//            log.info("一共重连 {} 次,无法连接数据库服务,服务停止！", times);
//            System.exit(1);
//        }
//        log.info("第 {} 次重连", times);
//        times++;
//        try {
//            Thread.sleep(Global.CONNECT_INTERVAL * times);
//        } catch (Exception e) {
//        }
//        return getRtmpList();
//    }
//}