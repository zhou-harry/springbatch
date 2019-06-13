package com.example.demo.flatFile.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author zhouhong
 * @version 1.0
 * @title: FlatFileStepListener
 * @description: TODO
 * @date 2019/6/13 15:41
 */
public class FlatFileStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("开始执行step："+stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if( stepExecution.getStatus() == BatchStatus.COMPLETED ){
            //job success
            System.out.println("执行Step成功。。。");
        }
        else if(stepExecution.getStatus() == BatchStatus.FAILED){
            //job failure
            System.out.println("执行Step失败。。。");
        }
        return ExitStatus.COMPLETED;
    }
}
