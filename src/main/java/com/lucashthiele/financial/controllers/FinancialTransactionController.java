package com.lucashthiele.financial.controllers;

import com.lucashthiele.financial.models.transaction.CreateTransactionData;
import com.lucashthiele.financial.services.FinancialTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class FinancialTransactionController {

    @Autowired
    FinancialTransactionService service;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody CreateTransactionData data){
        service.createTransaction(data);
        return ResponseEntity.ok().build();
    }
}
