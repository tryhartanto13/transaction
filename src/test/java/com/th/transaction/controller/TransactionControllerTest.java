package com.th.transaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.th.transaction.dto.TransactionRq;
import com.th.transaction.exception.GeneralException;
import com.th.transaction.service.TransactionService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @SneakyThrows
    @Test
    public void storeTrx_expectSuccess() {
        mockMvc.perform(post("/v1/storetrx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TransactionRq.builder()
                                .biller("GOPAY")
                                .amount(new BigDecimal("10000"))
                                .destNo("456456789")
                                .srcAccountNo("4567864568")
                                .destName("data")
                                .fee(new BigDecimal("1000"))
                                .transactionStatus(1)
                                .refNo("23211")
                                .build()
                        )))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void storeTrx_expectError() {
        doThrow(NullPointerException.class).when(transactionService).storeTransaction(any(TransactionRq.class));
        mockMvc.perform(post("/v1/storetrx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TransactionRq.builder()
                                .biller("GOPAY")
                                .amount(new BigDecimal("10000"))
                                .destNo("456456789")
                                .srcAccountNo("4567864568")
                                .destName("data")
                                .fee(new BigDecimal("1000"))
                                .transactionStatus(1)
                                .refNo("23211")
                                .build()
                        )))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    public void getTrx_expectSuccess() {
        mockMvc.perform(post("/v1/gettrx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TransactionRq.builder()
                                .biller("GOPAY")
                                .amount(new BigDecimal("10000"))
                                .destNo("456456789")
                                .srcAccountNo("4567864568")
                                .destName("data")
                                .fee(new BigDecimal("1000"))
                                .transactionStatus(1)
                                .refNo("23211")
                                .build()
                        )))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getTrx_expectError() {
        when(transactionService.getTransaction(any(TransactionRq.class))).thenThrow(NullPointerException.class);
        mockMvc.perform(post("/v1/gettrx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TransactionRq.builder()
                                .biller("GOPAY")
                                .amount(new BigDecimal("10000"))
                                .destNo("456456789")
                                .srcAccountNo("4567864568")
                                .destName("data")
                                .fee(new BigDecimal("1000"))
                                .transactionStatus(1)
                                .refNo("23211")
                                .build()
                        )))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    public void allTrx_expectSuccess() {
        mockMvc.perform(post("/v1/alltrx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TransactionRq.builder()
                                .biller("GOPAY")
                                .amount(new BigDecimal("10000"))
                                .destNo("456456789")
                                .srcAccountNo("4567864568")
                                .destName("data")
                                .fee(new BigDecimal("1000"))
                                .transactionStatus(1)
                                .refNo("23211")
                                .build()
                        )))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void allTrx_expectError() {
        when(transactionService.getAllTrx()).thenThrow(NullPointerException.class);
        mockMvc.perform(post("/v1/alltrx"))
                .andExpect(status().isBadRequest());
    }
}
