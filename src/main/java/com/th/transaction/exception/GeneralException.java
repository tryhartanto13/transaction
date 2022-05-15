package com.th.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private String errCode;
    private String errDesc;
    private String refNo;
}
