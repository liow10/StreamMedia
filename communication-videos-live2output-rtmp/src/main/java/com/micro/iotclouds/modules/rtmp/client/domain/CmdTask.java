/*
 * Copyright 2019 Pnoker. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.micro.iotclouds.modules.rtmp.client.domain;

import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;

import com.micro.iotclouds.modules.rtmp.client.service.CmdOutputService;
import com.micro.iotclouds.modules.rtmp.server.server.rtmp.Tools;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Copyright(c) 2019. Pnoker All Rights Reserved.
 * <p>
 * @Author : Pnoker
 * <p>
 * Email : pnokers@gmail.com
 * <p>
 * Description: Command 指令执行任务信息类
 */
@Data
@Slf4j
public class CmdTask {
	private String id;

	/**
	 * 任务运行状态
	 * <p>
	 * 0：初始化完成，等待被启动 <br>
	 * 1：启动完毕，正在运行中 <br>
	 * 2：任务错误，等待被重启 <br>
	 * 3：多次重启失败，任务已停止 <br>
	 * 4：任务停止 <br>
	 */
	private int status = 0;

	/**
	 * 任务累计被启动次数
	 */
	private int startTimes = 0;

	private String command;
	private Process process;
	private CmdOutputService cmdOutputService;

	public CmdTask(String command,String id) {
		this.id = id;
		this.status = 0;
		this.command = command;
	}

	/**
	 * 启动 CMD 任务 当任务启动次数超过最大次数，该任务不再被执行
	 */
	public void start() {
		if (startTimes < Global.MAX_TASK_TIMES) {
			try {
				log.info("启动 task->{} , command->{}", id, command);
				status = 1;
				String[] cmd = null;
				if (SystemUtils.IS_OS_WINDOWS) {
					cmd = new String[]{"cmd","/C",command}; 
				} else {
					cmd = new String[]{"/bin/sh","-c",command}; 
				}
					process = Runtime.getRuntime().exec(cmd);
				//process.waitFor();
				startTimes++;
				cmdOutputService = new CmdOutputService(id, process);
				Global.threadPoolExecutor.execute(cmdOutputService);
			} catch (Exception e) {
				status = 2;
				clear();
				create();
				log.error(e.getMessage(), e);
			}
		} else {
			clear();
			status = 3;
			log.error("任务 {} 达到最大重启次数，该任务不再执行", id);
		}
	}

	/**
	 * 停止执行 CMD 任务
	 */
	public boolean stop() {
		status = 4;
		if (null != cmdOutputService) {
			if (cmdOutputService.isStatus()) {
				cmdOutputService.setStatus(false);
				return true;
			}
		}
		return false;
	}

	/**
	 * 将任务放入队列，创建一个待启动的任务
	 */
	public boolean create() {
		if (!Global.cmdTaskIdQueue.offer(id)) {
			log.error("Current tasks queue is full,please try again later.");
			return false;
		}
		status = 0;
		return true;
	}

	/**
	 * 清理 Process 和 OutputService
	 */
	public void clear() {
		if (null != process) {
			process.destroyForcibly();
			process = null;
		}
		cmdOutputService = null;
	}
}