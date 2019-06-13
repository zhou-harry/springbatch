package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


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

	@Test
	public void testJob() throws Exception {
		JobLauncherTestUtils jobLauncherTestUtils=new JobLauncherTestUtils();
		jobLauncherTestUtils.setJobLauncher(jobLauncher);
		jobLauncherTestUtils.setJob(playerJob);

		Map<String, JobParameter> parameters=new LinkedHashMap<>();
		parameters.put("time",new JobParameter(System.currentTimeMillis()));

		JobExecution jobExecution = jobLauncherTestUtils.launchJob();


		Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

}
