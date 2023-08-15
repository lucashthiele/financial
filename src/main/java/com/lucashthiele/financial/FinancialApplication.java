package com.lucashthiele.financial;

import com.lucashthiele.financial.repositories.FinancialTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class FinancialApplication {

	@Autowired
	FinancialTransactionRepository financialTransactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(FinancialApplication.class, args);
	}

}
