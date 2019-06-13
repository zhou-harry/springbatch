package com.example.demo.flatFile.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author zhouhong
 * @version 1.0
 * @title: FlatFileJobListener
 * @description: TODO
 * @date 2019/6/12 17:50
 */
public class FlatFileJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("开始执行Job："+jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if( jobExecution.getStatus() == BatchStatus.COMPLETED ){
            //job success
            System.out.println("执行Job成功。。。");
        }
        else if(jobExecution.getStatus() == BatchStatus.FAILED){
            //job failure
            System.out.println("执行Job失败。。。");
        }
    }
}
