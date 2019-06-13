package com.example.demo.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhouhong
 * @version 1.0
 * @title: JobController
 * @description: TODO
 * @date 2019/6/10 11:11
 */
@RestController
public class JobController {

    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    JobOperator jobOperator;
    @Autowired
    Job playerJob;
    @Autowired
    Job xmlTradeJob;
    @Autowired
    Job jsonTradeJob;
    @Autowired
    Job jdbcCreditJob;

    @RequestMapping("/playerJob")
    public void playerJob() throws Exception{
        Map<String, JobParameter> parameters=new LinkedHashMap<>();
        parameters.put("time",new JobParameter(System.currentTimeMillis()));
        parameters.put("input_file_path",new JobParameter("src/main/resources/flatFile/players.csv"));

        jobLauncher.run(playerJob, new JobParameters(parameters));
    }

    @GetMapping("/playerJob/stop")
    public void stopPlayerJob() throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
        Set<Long> executions = jobOperator.getRunningExecutions("player_Job");
        jobOperator.stop(executions.iterator().next());
    }

    @RequestMapping("/xmlTradeJob")
    public void xmlTradeJob() throws Exception{
        Map<String, JobParameter> parameters=new LinkedHashMap<>();
        parameters.put("time",new JobParameter(System.currentTimeMillis()));

        jobLauncher.run(xmlTradeJob, new JobParameters(parameters));
    }

    @RequestMapping("/jsonTradeJob")
    public void jsonTradeJob() throws Exception{
        Map<String, JobParameter> parameters=new LinkedHashMap<>();
        parameters.put("time",new JobParameter(System.currentTimeMillis()));

        jobLauncher.run(jsonTradeJob, new JobParameters(parameters));
    }

    @RequestMapping("/jdbcCreditJob")
    public void jdbcCreditJob() throws Exception{
        Map<String, JobParameter> parameters=new LinkedHashMap<>();
        parameters.put("time",new JobParameter(System.currentTimeMillis()));

        jobLauncher.run(jdbcCreditJob, new JobParameters(parameters));
    }

}
