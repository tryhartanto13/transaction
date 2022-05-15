package com.th.transaction.service;

import com.th.transaction.dto.TransactionRq;
import com.th.transaction.dto.TransactionRs;
import com.th.transaction.entity.Transaction;
import com.th.transaction.repository.TransactionRepository;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @Autowired
    private EmbeddedCacheManager ecm;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionService transactionService;

    @Test
    public void storeTrx_expectSuccess(){
        transactionService.storeTransaction(TransactionRq.builder()
                .biller("GOPAY")
                .amount(new BigDecimal("10000"))
                .destNo("456456789")
                .srcAccountNo("4567864568")
                .destName("data")
                .fee(new BigDecimal("1000"))
                .transactionStatus(1)
                .refNo("23211")
                .build());
        Transaction transaction = transactionRepository.getTransactionByRefNo("23211");
        Assert.assertNotNull(transaction);
    }

    @Test
    public void updateTrx_expectSuccess(){
        transactionService.storeTransaction(TransactionRq.builder()
                .biller("GOPAY")
                .amount(new BigDecimal("10000"))
                .destNo("456456789")
                .srcAccountNo("4567864568")
                .destName("data")
                .fee(new BigDecimal("1000"))
                .transactionStatus(1)
                .refNo("23212")
                .build());
        Transaction transaction = transactionRepository.getTransactionByRefNo("23212");
        transactionService.storeTransaction(TransactionRq.builder().transactionId(transaction.getTransactionId()).transactionStatus(2).build());
        transaction = transactionRepository.getTransactionByRefNo("23212");
        Assert.assertEquals(2, transaction.getTransactionStatus());
    }

    @Test
    public void getTrx_expectSuccess(){
        transactionService.storeTransaction(TransactionRq.builder()
                .biller("GOPAY")
                .amount(new BigDecimal("10000"))
                .destNo("456456789")
                .srcAccountNo("4567864568")
                .destName("data")
                .fee(new BigDecimal("1000"))
                .transactionStatus(1)
                .refNo("23211")
                .build());
        TransactionRs transactionRs = transactionService.getTransaction(TransactionRq.builder().refNo("23211").build());
        Assert.assertNotNull(transactionRs);
    }

    @After
    public void destroy(){
        transactionRepository.deleteAll();
    }
}
