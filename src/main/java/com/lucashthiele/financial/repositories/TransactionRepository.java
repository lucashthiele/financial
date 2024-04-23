package com.lucashthiele.financial.repositories;

import com.lucashthiele.financial.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findFinancialTransactionById(Integer id);
}
