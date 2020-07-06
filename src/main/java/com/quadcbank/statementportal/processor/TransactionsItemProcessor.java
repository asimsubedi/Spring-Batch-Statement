package com.quadcbank.statementportal.processor;

import org.springframework.batch.item.ItemProcessor;

import com.quadcbank.statementportal.model.Transactions;

import lombok.extern.slf4j.Slf4j;

/**
 * @author AsimSubedi
 *
 */
@Slf4j
public class TransactionsItemProcessor implements ItemProcessor<Transactions, Transactions> {

	@Override
	public Transactions process(Transactions item) throws Exception {
		
		log.info(">>> Inside TransactionsItemProcessor!!");
		return item;
		
	}

}
