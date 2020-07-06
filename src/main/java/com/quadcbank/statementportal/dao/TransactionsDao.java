/**
 * 
 */
package com.quadcbank.statementportal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author AsimSubedi
 *
 */
@Repository
public class TransactionsDao {
	
	@Autowired
	private DataSource dataSource;
	
	public void updateTransactions() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.batchUpdate("UPDATE cc_transactions SET status=\'COMPLETED\' WHERE status=\'PENDING\'");
		
		
	}

}
