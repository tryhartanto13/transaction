package com.th.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class TransactionRq implements Serializable {
    private Long transactionId;
    private String srcAccountNo;
    private String destNo;
    private String destName;
    private String biller;
    private BigDecimal amount;
    private BigDecimal fee;
    private String refNo;
    private int transactionStatus;
}
