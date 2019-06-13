package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Autowired
	JobOperator jobOperator;
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job multiTradeJob;

	@Scheduled(fixedDelay = 50000)
	public void test() throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");

		Map<String, JobParameter> parameters = new LinkedHashMap<>();
		parameters.put("time", new JobParameter(sf.format(new Date(System.currentTimeMillis()))));
        JobExecution jobExecution = jobLauncher.run(multiTradeJob, new JobParameters(parameters));
        System.out.println("Job执行完成："+jobExecution.getExitStatus().getExitCode());

//		Long nextInstance = jobOperator.startNextInstance("player_Job");//
//		System.out.println("Job执行完成："+nextInstance);
	}

}
