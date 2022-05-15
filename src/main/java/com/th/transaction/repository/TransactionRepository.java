package com.th.transaction.repository;

import com.th.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Modifying
    @Query(value = "UPDATE TRANSACTION SET TRANSACTION_STATUS=?1, PAYMENT_DATE=?3 WHERE TRANSACTION_ID=?2", nativeQuery = true)
    void updateTransactionStatus(int transactionStatus, Long transactionId, LocalDateTime now);

    @Query(value = "select t from Transaction t where refNo=?1")
    Transaction getTransactionByRefNo(String refNo);
}
