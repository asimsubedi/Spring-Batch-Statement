package com.quadcbank.statementportal.schedular;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author AsimSubedi
 *
 */
@Service
@EnableScheduling
@Slf4j
public class JobSchedularService {

	@Autowired
	@Qualifier("creationJob")
	private Job insertJob;

	@Autowired
	@Qualifier("updateJob")
	private Job updateJob;

	@Autowired
	private JobLauncher jobLauncher;

	@Scheduled(cron = "${insert-time}")
	public void runTransactionsInsertJob() {
		
		log.info("Insertion Job Triggered!!!");

		JobParameters params = new JobParametersBuilder().addLong("jobId", System.currentTimeMillis())
				.toJobParameters();
		try {
			jobLauncher.run(insertJob, params);

		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}

	}
	
	@Scheduled(cron = "${update-time}")
	public void runTransactionsUpdateJob() {

		log.info("Update Job Triggered!!!");
		JobParameters params = new JobParametersBuilder().addLong("jobId", System.currentTimeMillis())
				.toJobParameters();
		try {
			jobLauncher.run(updateJob, params);

		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}

	}

}
