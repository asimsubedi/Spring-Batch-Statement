package com.quadcbank.statementportal.processor;

import org.springframework.batch.item.ItemProcessor;

import com.quadcbank.statementportal.model.Transactions;

/**
 * @author AsimSubedi
 *
 */
public class TransactionsItemProcessor implements ItemProcessor<Transactions, Transactions> {

	@Override
	public Transactions process(Transactions item) throws Exception {
		
		System.out.println(">>> Inside TransactionsItemProcessor!!");
		return item;
		
	}

}
