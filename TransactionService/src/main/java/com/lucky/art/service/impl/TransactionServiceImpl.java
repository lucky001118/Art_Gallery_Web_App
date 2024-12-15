package com.lucky.art.service.impl;

import com.lucky.art.externalModel.Orders;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalService.OrderService;
import com.lucky.art.externalService.SellerService;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.Transaction;
import com.lucky.art.repository.TransactionRepository;
import com.lucky.art.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final SellerService sellerService;
    private final UserService userService;
    private final OrderService orderService;
    @Override
    public Transaction createTransaction(Orders orders) throws Exception {
        Seller seller = sellerService.getSellerById(orders.getSellerId());
        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setSellerId(seller.getId());
        transaction.setCustomers(orders.getUser());
        transaction.setCustomerId(orders.getUser().getId());
        transaction.setOrder(orders);
        transaction.setOrderId(orders.getId());

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }
}
