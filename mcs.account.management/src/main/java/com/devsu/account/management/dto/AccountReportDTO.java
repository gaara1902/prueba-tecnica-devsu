package com.devsu.account.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AccountReportDTO {

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_type")
    private String accountType;

    @JsonProperty("transactions")
    private List<TransactionReportDTO> transactions;

    @JsonProperty("initial_balance")
    private Double initialBalance;

    @JsonProperty("total_credits")
    private Double totalCredits;

    @JsonProperty("total_debits")
    private Double totalDebits;

    private Double balance;
}
