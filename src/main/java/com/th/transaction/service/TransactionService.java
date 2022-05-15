package com.th.transaction.service;

import com.th.transaction.dto.TransactionRq;
import com.th.transaction.dto.TransactionRs;
import com.th.transaction.entity.Transaction;
import com.th.transaction.repository.TransactionRepository;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EmbeddedCacheManager ecm;

    @Transactional
    public void storeTransaction(TransactionRq transactionRq) {
        if (transactionRq.getTransactionId() == null) {
            Transaction transaction = Transaction.builder()
                    .srcAccountNo(transactionRq.getSrcAccountNo())
                    .destNo(transactionRq.getDestNo())
                    .destName(transactionRq.getDestName())
                    .biller(transactionRq.getBiller())
                    .amount(transactionRq.getAmount())
                    .fee(transactionRq.getFee())
                    .refNo(transactionRq.getRefNo())
                    .transactionStatus(transactionRq.getTransactionStatus())
                    .build();
            transactionRepository.save(transaction);
            ecm.getCache("transaction").put(transaction.getRefNo(), transaction, 1L, TimeUnit.MINUTES);
        } else {
            transactionRepository.updateTransactionStatus(transactionRq.getTransactionStatus(), transactionRq.getTransactionId());
        }
    }

    public TransactionRs getTransaction(TransactionRq transactionRq) {
        Transaction transaction = (Transaction) ecm.getCache("transaction").get(transactionRq.getRefNo());
        if (transaction == null) {
            transaction = transactionRepository.getTransactionByRefNo(transactionRq.getRefNo());
        }
        return TransactionRs.builder()
                .transactionId(transaction.getTransactionId())
                .srcAccountNo(transaction.getSrcAccountNo())
                .destNo(transaction.getDestNo())
                .destName(transaction.getDestName())
                .biller(transaction.getBiller())
                .amount(transaction.getAmount())
                .fee(transaction.getFee())
                .refNo(transaction.getRefNo())
                .transactionStatus(transaction.getTransactionStatus())
                .build();
    }
}
