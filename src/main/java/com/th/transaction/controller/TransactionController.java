package com.th.transaction.controller;

import com.th.transaction.dto.TransactionRq;
import com.th.transaction.dto.TransactionRs;
import com.th.transaction.exception.GeneralException;
import com.th.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/storetrx")
    @ResponseStatus(code = HttpStatus.OK)
    public void storetrx(@RequestBody TransactionRq transactionRq) {
        try {
            transactionService.storeTransaction(transactionRq);
        } catch (Exception ex) {
            log.error("Exception happened when get transaction, message [{}]",ex.getMessage());
            throw GeneralException.builder().refNo(transactionRq.getRefNo()).build();
        }
    }

    @PostMapping(value = "/gettrx")
    @ResponseBody
    public TransactionRs gettrx(@RequestBody TransactionRq transactionRq) {
        try {
            return transactionService.getTransaction(transactionRq);
        } catch (Exception ex) {
            log.error("Exception happened when get transaction, message [{}]",ex.getMessage());
            throw GeneralException.builder().refNo(transactionRq.getRefNo()).build();
        }
    }

    @PostMapping(value = "/alltrx")
    @ResponseBody
    public List<TransactionRs> getAllTrx() {
        try {
            return transactionService.getAllTrx();
        } catch (Exception ex) {
            throw new GeneralException();
        }
    }

}
