package com.example.demo.quartz.customer.job;

import org.quartz.*;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author harry
 * @version 1.0
 * @title: BatchJob
 * @description: TODO
 * @date 2019/6/16 14:05
 */
public class BatchJob extends QuartzJobBean {

    static Logger logger = LoggerFactory.getLogger(SampleJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        logger.info("quartz batch job开启：" + LocalDateTime.now());
        logger.info("定时任务Job Key ： {}", context.getJobDetail().getKey());

        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");

        Map<String, JobParameter> parameters = new LinkedHashMap<>();
        parameters.put("time", new JobParameter(sf.format(new Date(System.currentTimeMillis()))));

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String jobName = (String) jobDataMap.get("multiTradeJob");
        JobExecution jobExecution = null;
        try {
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            JobLauncher jobLauncher = (JobLauncher)schedulerContext.get("jobLauncher");
            JobRegistry jobRegistry = (JobRegistry)schedulerContext.get("jobRegistry");

            Job job = jobRegistry.getJob(jobName);

            jobExecution = jobLauncher.run(job, new JobParameters(parameters));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return;
        }catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (NoSuchJobException e) {
            e.printStackTrace();
        }

        logger.info("Job执行完成："+jobExecution.getExitStatus().getExitCode());
    }
}
