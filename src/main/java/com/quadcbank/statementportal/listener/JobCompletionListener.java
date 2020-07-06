/**
 * 
 */
package com.quadcbank.statementportal.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author AsimSubedi
 *
 */
@Component
public class JobCompletionListener implements JobExecutionListener {
	
	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println(">>> Executing Job ID: " + jobExecution.getId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println(">>> Completion of Job");
		}
	}

}
