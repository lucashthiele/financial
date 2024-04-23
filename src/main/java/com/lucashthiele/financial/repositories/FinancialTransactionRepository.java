package com.lucashthiele.financial.repositories;

import com.lucashthiele.financial.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialTransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findFinancialTransactionById(Integer id);
}
