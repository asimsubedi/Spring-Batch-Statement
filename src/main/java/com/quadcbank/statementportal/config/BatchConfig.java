package com.quadcbank.statementportal.config;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.quadcbank.statementportal.listener.JobCompletionListener;
import com.quadcbank.statementportal.model.Transactions;
import com.quadcbank.statementportal.processor.TransactionsItemProcessor;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author AsimSubedi
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@SuppressWarnings("rawtypes")
	@Bean
	public LineMapper<Transactions> lineMapper() {

		DefaultLineMapper<Transactions> lineMapper = new DefaultLineMapper<Transactions>();

		lineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
			{
				setNames(new String[] { "date", "merchant", "amount", "location" });
			}
		});

		lineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<Transactions>() {
			{
				
				// converting string date to Date format for date field...
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

				CustomDateEditor customDate = new CustomDateEditor(df, false);
				HashMap<Class, PropertyEditor> customEditor = new HashMap<Class, PropertyEditor>();

				customEditor.put(Date.class, customDate);
				setTargetType(Transactions.class);
				setCustomEditors(customEditor);

			}
		});

		return lineMapper;

	}

	@Bean
	public FlatFileItemReader<Transactions> reader() {
		return new FlatFileItemReaderBuilder<Transactions>().name("transactionsItemReader")
				.resource(new ClassPathResource("statement.csv")).lineMapper(lineMapper()).linesToSkip(1).build();
	}

	@Bean
	public JdbcBatchItemWriter<Transactions> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Transactions>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Transactions>())
				.sql("INSERT INTO cc_transactions (date, merchant, amount, location) VALUES (:date, :merchant, :amount, :location)")
				.dataSource(dataSource).build();
	}

	@Bean
	public ItemProcessor<Transactions, Transactions> processor() {
		return new TransactionsItemProcessor();
	}

	@Bean
	public Job createTransactionsJob(JobCompletionListener listener, Step step1) {
		return jobBuilderFactory.get("createTransactionsJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	public Step step1(ItemReader<Transactions> reader, ItemWriter<Transactions> writer,
			ItemProcessor<Transactions, Transactions> processor) {
		return stepBuilderFactory.get("step1").<Transactions, Transactions>chunk(1).reader(reader).processor(processor)
				.writer(writer).build();
	}

	@Bean
	public DataSource getDataSource() {
		HikariDataSource dataSource = new HikariDataSource();

		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/nivtekdb");
		dataSource.setUsername("nivtekdev");
		dataSource.setPassword("nivtekdev");

		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Override
	public void setDataSource(DataSource datasource) {

	}

}
