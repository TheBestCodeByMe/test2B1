package com.example.test2b1.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class AllInformation {
    private Long id;
    private String nameBank;
    private String currency;
    private String nameFile;
    private Date dateSince;
    private Date dateTo;
    private LocalDate fillingTime;
    private int balanceAccNumb;
    private int codeBalAcc;
    private String DescriptionCl;
    private BigDecimal activeAmountInc;
    private BigDecimal passiveAmountInc;
    private BigDecimal activeAmountOut;
    private BigDecimal passiveAmountOut;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
}
