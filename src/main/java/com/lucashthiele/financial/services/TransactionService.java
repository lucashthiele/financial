package com.lucashthiele.financial.services;

import com.lucashthiele.financial.models.transaction.CreateTransactionData;
import com.lucashthiele.financial.models.transaction.Transaction;
import com.lucashthiele.financial.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public void createTransaction(CreateTransactionData data) {
        var transaction = new Transaction(null, data.value(), data.type(), data.origin());
        repository.save(transaction);
    }
}
