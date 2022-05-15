package com.th.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
    @Id
    @SequenceGenerator(name = "transactionSeq", sequenceName = "transactionSeq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionGenerator")
    private Long transactionId;
    private String srcAccountNo;
    private String destNo;
    private String destName;
    private String biller;
    private BigDecimal amount;
    private BigDecimal fee;
    private String refNo;
    private LocalDateTime inquiryDate;
    private LocalDateTime paymentDate;
    private int transactionStatus;
}
