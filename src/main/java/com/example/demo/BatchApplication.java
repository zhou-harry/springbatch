package com.example.demo;

import com.example.demo.quartz.ApiBootQuartzService;
import com.example.demo.quartz.support.ApiBootQuartzServiceDefaultSupport;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}


	@Autowired
	JobOperator jobOperator;
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	JobRegistry jobRegistry;
	@Autowired
	Job multiTradeJob;

	@Bean
	public ApiBootQuartzService apiBootQuartzService(Scheduler scheduler) throws SchedulerException {
		SchedulerContext context = scheduler.getContext();
		context.put("jobLauncher",jobLauncher);
		context.put("jobRegistry",jobRegistry);

		return new ApiBootQuartzServiceDefaultSupport(scheduler);
	}


//	@Scheduled(fixedDelay = 50000)
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
