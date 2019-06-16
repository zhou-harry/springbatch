package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.quartz.ApiBootQuartzService;
import com.example.demo.quartz.customer.job.BatchJob;
import com.example.demo.quartz.customer.job.SampleJob;
import com.example.demo.quartz.wrapper.ApiBootJobParamWrapper;
import com.example.demo.quartz.wrapper.support.ApiBootCronJobWrapper;
import com.example.demo.quartz.wrapper.support.ApiBootLoopJobWrapper;
import com.example.demo.quartz.wrapper.support.ApiBootOnceJobWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author harry
 * @version 1.0
 * @title: QuartzController
 * @description: TODO
 * @date 2019/6/16 13:46
 */
@RestController
public class QuartzController {


    @Autowired
    private ApiBootQuartzService apiBootQuartzService;

    /**
     * 创建loop 任务
     *
     * @return 任务 Job Key
     */
    @GetMapping(value = "/start-loop-job")
    public String startLoopJob() {
        return apiBootQuartzService.newJob(
                ApiBootLoopJobWrapper.Context()
                        // 参数
                        .param(
                                ApiBootJobParamWrapper.wrapper()
                                        .put("userName", "harry")
                                        .put("userAge", 24)
                        )
                        // 每次循环的间隔时间，单位：毫秒
                        .loopIntervalTime(2000)
                        // 循环次数
                        .repeatTimes(5)
                        // 开始时间，10秒后执行
                        .startAtTime(new Date(System.currentTimeMillis() + 10000))
                        // 任务类
                        .jobClass(SampleJob.class)
                        .wrapper()
        );
    }

    /**
     * 创建Once Job
     *
     * @return 任务Job Key
     */
    @GetMapping(value = "/start-once-job")
    public String startOnceJob() {
        Map paramMap = new HashMap(1);
        paramMap.put("paramKey", "参数值");

        return apiBootQuartzService.newJob(
                ApiBootOnceJobWrapper.Context()
                        .jobClass(SampleJob.class)
                        // 参数
                        .param(
                                ApiBootJobParamWrapper.wrapper()
                                        .put("mapJson", JSON.toJSONString(paramMap))
                        )
                        // 开始时间，2秒后执行
                        .startAtTime(new Date(System.currentTimeMillis() + 10000))
                        .wrapper()
        );
    }

    /**
     * 创建Cron Job
     *
     * @return 任务Job Key
     */
    @GetMapping(value = "/start-cron-job")
    public String startCronJob() {

        return apiBootQuartzService.newJob(
                ApiBootCronJobWrapper.Context()
                        .jobClass(SampleJob.class)
                        .cron("0/5 * * * * ?")
                        .param(
                                ApiBootJobParamWrapper.wrapper()
                                        .put("param", "测试")
                        )
                        .wrapper()
        );
    }

    /**
     * 创建批处理Job
     *
     * @return
     */
    @GetMapping(value = "/start-batch-job")
    public String startBatchJob() {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("multiTradeJob", "multiTradeJob");

        return apiBootQuartzService.newJob(
                ApiBootOnceJobWrapper.Context()
                        .jobClass(BatchJob.class)
                        // 参数
                        .param(ApiBootJobParamWrapper.wrapper().put(paramMap))
                        // 开始时间，10秒后执行
                        .startAtTime(new Date(System.currentTimeMillis() + 10000))
                        .wrapper()
        );
    }

    /**
     * 删除任务
     *
     * @param jobKey 任务 Job Key
     */
    @GetMapping(value = "/delete-job")
    public void deleteJob(String jobKey) {
        apiBootQuartzService.deleteJob(jobKey);
    }

    /**
     * 暂停任务
     *
     * @param jobKey 任务Job Key
     */
    @GetMapping(value = "/pause-job")
    public void pauseJob(String jobKey) {
        apiBootQuartzService.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     *
     * @param jobKey 任务Job Key
     */
    @GetMapping(value = "/resume-job")
    public void resumeJob(String jobKey) {
        apiBootQuartzService.resumeJob(jobKey);
    }

    /**
     * 更新Cron任务的Cron表达式
     *
     * @param jobKey 任务 Job Key
     */
    @GetMapping(value = "/update-cron")
    public void updateCron(String jobKey) {
        apiBootQuartzService.updateJobCron(jobKey, "0/5 * * * * ?");
    }
}
