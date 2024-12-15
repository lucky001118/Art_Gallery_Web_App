package com.lucky.art.service;

import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Orders orders) throws Exception;
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsBySellerId(Seller seller);


}
