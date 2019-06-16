package com.example.demo.quartz.customer.job;

import com.alibaba.fastjson.JSON;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author harry
 * @version 1.0
 * @title: SampleJob
 * @description: TODO
 * @date 2019/6/16 13:09
 */
@Component
public class SampleJob extends QuartzJobBean {

    static Logger logger = LoggerFactory.getLogger(SampleJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        logger.info("quartz job开启：" + LocalDateTime.now());
        logger.info("定时任务Job Key ： {}", context.getJobDetail().getKey());
        logger.info("定时任务执行时所携带的参数：{}", JSON.toJSONString(context.getJobDetail().getJobDataMap()));
    }
}
