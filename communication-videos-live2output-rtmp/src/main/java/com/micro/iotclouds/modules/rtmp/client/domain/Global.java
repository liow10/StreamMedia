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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Copyright(c) 2019. Pnoker All Rights Reserved.
 * <p>@Author    : Pnoker
 * <p>Email      : pnokers@gmail.com
 * <p>Description: Global，用于存储Task队列和任务信息
 */
@Slf4j
public class Global {
    //重连时间间隔 & 最大重连次数
    public static int CONNECT_INTERVAL = 1000 * 5;
    public static int CONNECT_MAX_TIMES = 10;

    //记录Task信息
    public static int MAX_TASK_SIZE = 5000;
    public static int MAX_TASK_TIMES = 10;
    public static Map<String, CmdTask> taskMap = new HashMap(5000);
    public static LinkedBlockingQueue<String> cmdTaskIdQueue = new LinkedBlockingQueue(5000);

    /**
     * 核心线程池大小
     */
    public static int corePoolSize = 4;

    /**
     * 最大线程池大小
     */
    public static int maximumPoolSize = 5000;

    /**
     * 线程最大空闲时间
     */
    public static long keepAliveTime = 10;

    /**
     * 时间单位，秒
     */
    public static TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 用于缓存 Cmd任务 线程
     */
    public static LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue(64);

    /**
     * Cmd任务线程池，用于全部任务线程使用
     */
    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, linkedBlockingQueue, new RtmpTaskTreadFactory(), new IgnorePolicy());

    static class RtmpTaskTreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "dc3-rtmp-thread-" + mThreadNum.getAndIncrement());
            log.info("{} has been created", thread.getName());
            return thread;
        }
    }

    static class IgnorePolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            log.error("{} rejected,completedTaskCount:{}", r.toString(), e.getCompletedTaskCount());
        }
    }

}
