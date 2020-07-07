package com.quadcbank.statementportal;

import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class StatementPortalApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(StatementPortalApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		log.info(">> App Started with args: {}" , Arrays.toString(args.getSourceArgs())  );
		
	}

}
