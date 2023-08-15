package com.lucashthiele.financial.repositories;

import com.lucashthiele.financial.models.transaction.FinancialTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface FinancialTransactionRepository extends MongoRepository<FinancialTransaction, String> {
    @Query("{id:'?0'}")
    FinancialTransaction findFinancialTransactionById(String id);
}
