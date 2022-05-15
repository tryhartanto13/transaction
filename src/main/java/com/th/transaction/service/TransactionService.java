package com.th.transaction.service;

import com.th.transaction.dto.TransactionRq;
import com.th.transaction.dto.TransactionRs;
import com.th.transaction.entity.Transaction;
import com.th.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
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
                    .inquiryDate(LocalDateTime.now())
                    .transactionStatus(transactionRq.getTransactionStatus())
                    .build();
            transactionRepository.save(transaction);
            ecm.getCache("transaction").put(transaction.getRefNo(), transaction, 1L, TimeUnit.MINUTES);
        } else {
            transactionRepository.updateTransactionStatus(transactionRq.getTransactionStatus(), transactionRq.getTransactionId(), LocalDateTime.now());
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

    public List<TransactionRs> getAllTrx() {
        List<Transaction> listTrx = transactionRepository.findAll();
        log.info("all transaction [{}]", listTrx);
        return listTrx.stream().map(trx -> new TransactionRs(trx.getTransactionId(), trx.getSrcAccountNo(),
                        trx.getDestNo(), trx.getDestName(), trx.getBiller(), trx.getAmount(), trx.getFee(), trx.getRefNo(), trx.getTransactionStatus()))
                .collect(Collectors.toList());
    }
}
