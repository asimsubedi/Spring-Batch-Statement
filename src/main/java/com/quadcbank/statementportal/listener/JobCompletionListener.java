package com.quadcbank.statementportal.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author AsimSubedi
 *
 */
@Component
@Slf4j
public class JobCompletionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.STARTED)
			log.info(">>> Executing Job ID: " + jobExecution.getId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) 
			log.info(">>> Completion of Job Id: " + jobExecution.getId());
		
	}

}
